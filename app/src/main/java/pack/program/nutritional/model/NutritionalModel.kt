package pack.program.nutritional.model

data class NutritionalModel(
    val hits: List<Hit>,
    val max_score: Double,
    val total: Int
)