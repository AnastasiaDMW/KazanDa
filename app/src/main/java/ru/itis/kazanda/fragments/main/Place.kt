package ru.itis.kazanda.fragments.main

data class Place(
    val id: Int,
    val name: String,
    val payment: Int,
    //maybe 0 - free
    //1 - $
    //2 - $$
    //3 - $$$
    val address: String,
    val hours: String,
    val description: String,
    val imageUrls: String,
)
//// Data class для хранения информации о местах
//data class Place(
//    val id: Int,
//    val name: String,
//    val payment: Int, // 0 - бесплатно, 1 - $, 2 - $$, 3 - $$$
//    val address: String,
//    val hours: String,
//    val description: String,
//    val image: String // Путь к изображению или URL
//)
//
//// Репозиторий для хранения и предоставления данных о местах
//object PlaceRepository {
//    val places = listOf(
//        Place(1, "Казанский Кремль", 0, "Казань, Кремлёвская ул., 2", "09:00 - 18:00", "Описание Казанского Кремля", "image_kremlin"),
//        Place(2, "Кул Шариф", 0, "Казань, Кремлёвская ул., 2", "08:00 - 20:00", "Описание мечети Кул Шариф", "image_kul_sharif"),
//        Place(3, "Башня Сююмбике", 0, "Казань, Кремлёвская ул., 2", "09:00 - 17:00", "Описание Башни Сююмбике", "image_syuumbike"),
//        // Добавьте больше мест по аналогии
//    )
//}
//
//// Адаптер для RecyclerView
//class PlaceAdapter(private val places: List<Place>) : RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {
//
//    // ViewHolder для элемента списка
//    class PlaceViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(place: Place) {
//            view.findViewById<TextView>(R.id.placeName).text = place.name
//            // Привязка остальных данных места к элементам View
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
//        return PlaceViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
//        holder.bind(places[position])
//    }
//
//    override fun getItemCount() = places.size
//}
//
//// Фрагмент для отображения главного экрана со списком мест
//class MainScreenFragment : Fragment() {
//
//    private lateinit var recyclerView: RecyclerView
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_main_screen, container, false)
//        recyclerView = view.findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = GridLayoutManager(context, 2)
//        recyclerView.adapter = PlaceAdapter(PlaceRepository.places)
//        return view
//    }
//}
//
//// Фрагмент для отображения подробного описания места
//class DetailScreenFragment : Fragment() {
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_detail_screen, container, false)
//        // Получение данных о месте и их отображение
//        return view
//    }
//}
