package pack.program.nutritional.model

data class Hit(
    val _id: String,
    val _index: String,
    val _score: Double,
    val _type: String,
    val fields: Fields
)