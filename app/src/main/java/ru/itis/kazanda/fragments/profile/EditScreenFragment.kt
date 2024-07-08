package ru.itis.kazanda.fragments.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import java.io.FileOutputStream
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.core.content.edit
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.FragmentEditScreenBinding
import java.io.File


class EditScreenFragment : Fragment(R.layout.fragment_edit_screen) {

    private var binding: FragmentEditScreenBinding? = null
    private val REQUEST_CODE_1 = 1
    private val REQUEST_CODE_2 = 2
    var selectedImage : Uri? = null
    var selectedBitmap : Bitmap? = null
    var changePhoto : Boolean = false //отслеживает, поменял ли пользователь аву

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditScreenBinding.bind(view)

        val pref = context?.getSharedPreferences("Default", Context.MODE_PRIVATE)
        val filesDir = requireContext().filesDir
        val imagePath = File(filesDir, "image.png")

        binding?.apply {

            if (imagePath.exists()) {
                Glide.with(requireContext())
                    .load(Uri.fromFile(imagePath))
                    .circleCrop()
                    .into(ivAvatar)
                ivIcon.setVisibility(View.INVISIBLE)
            }

            ivAddPhoto.setOnClickListener {
                activity?.let {
                    //если разрешение не было дано ранее
                    if (ContextCompat.checkSelfPermission(
                            it.applicationContext,
                            Manifest.permission.READ_MEDIA_IMAGES
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        /*то запросить его. После того, как пользователь ответит на диалоговое окно, система вызовет
                        реализацию метода onRequestPermissionsResult(), которая приведена в моем коде ниже*/
                        ActivityCompat.requestPermissions(requireActivity(),
                            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                            REQUEST_CODE_1
                        )
                    } else {
                        /*если разрешение было дано ранее, то создаем Intent(рус. намерение)
                         чтобы открыть галерею. ACTION_PICK указывает на то, что мы хотим выбрать данные,
                         а MediaStore.Images.Media.EXTERNAL_CONTENT_URI - это URI контента, представляющий все изображения
                         на внешнем хранилище устройства.*/
                        val intent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        //этот метод запускает новое действие, потом вызовется реализация метода onActivityResult(), приведенная ниже
                        startActivityForResult(intent, REQUEST_CODE_2)
                    }
                }
            }

            confirmButton.setOnClickListener {
                when {
                    //1. если пользователь меняет только фото (не меняет имя и емейл), то мы не обновляем занчения в SharedPreferences
                    changePhoto && etEmail.text.isNullOrEmpty() && etName.text.isNullOrEmpty() -> {}

                    //2. если пользователь поменял ТОЛЬКО имя(возможно, поменял фото, но нам это здесь не важно)
                    !etName.text.isNullOrEmpty() && etEmail.text.isNullOrEmpty()-> {
                        pref?.edit {
                            putString(ARG_NAME, etName.text.toString())
                        }
                    }

                    //3. если пользователь поменят ТОЛЬКО емейл (опять же, фото неважно)
                    !etEmail.text.isNullOrEmpty() && etName.text.isNullOrEmpty() -> {
                        pref?.edit {
                            putString(ARG_EMAIL, etEmail.text.toString())
                        }
                    }

                    //4. если пользователь поменя и имя, и емейл
                    !etEmail.text.isNullOrEmpty() && !etName.text.isNullOrEmpty() -> {
                        pref?.edit {
                            putString(ARG_NAME, etName.text.toString())
                            putString(ARG_EMAIL, etEmail.text.toString())
                        }
                    }

                    //если пользователь ничего не поменял(ни фото, ни имя, ни емейл)
                    else -> {
                        Snackbar.make(root, "Никаких изменений не было внесено", Snackbar.LENGTH_LONG).show()
                    }
                }
                findNavController().navigate(R.id.action_editScreenFragment_to_profileScreenFragment)
                changePhoto = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //проверяем код запроса
        if (requestCode == REQUEST_CODE_1) {
            //Если запрос на разрешение отклонен, то result arrays пустые.
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, REQUEST_CODE_2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //снова проверяем код запроса, а также что действие, запущенное с помощью startActivityForResult(), завершилась успешно.
        if (requestCode == REQUEST_CODE_2 && resultCode == Activity.RESULT_OK && data != null) {
            //берем uri выбранного пользователем изображения, если оно не null
            selectedImage = data.data
        }
        try {
            context?.let {
                /*если uri не null, то кодируем его в bitmap
                contentResolver предоставляет доступ к контент-провайдеру(рус. поставщик содержимого)*/
                if (selectedImage != null) {
                    /*Нужно обратить внимание на версию телефона андроид,
                    т.к. процессы доступа к галерее зависят от версии*/
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { //28
                        //тут мы используем декодер, более современный и гибкий способ декодирования изображений
                        val source = ImageDecoder.createSource(it.contentResolver, selectedImage!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                    } else {
                        /*тут используется устаревший класс MediaStore для получения растрововго изображения
                        Вызывая getBitmap(), мы запрашиваем растровое изображение выбранного изображения из контент-провайдера изображений.*/
                        selectedBitmap = MediaStore.Images.Media.getBitmap(it.contentResolver, selectedImage)
                    }
                }
                //сохранение изображения в файл image.png
                val outputStream = it.openFileOutput("image.png", Context.MODE_PRIVATE)
                //сжимаем растровое изображение в формате png с качеством сжатия 100(т.е. без сжатия по сути) и записываем в outputStream
                selectedBitmap?.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            }
            if (selectedBitmap != null) {
                changePhoto = true
                binding?.apply {
                    ivIcon.setVisibility(View.INVISIBLE)
                    ivAvatar.setImageBitmap(selectedBitmap)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val ARG_NAME = "ARG_NAME"
        private const val ARG_EMAIL = "ARG_EMAIL"
    }
}