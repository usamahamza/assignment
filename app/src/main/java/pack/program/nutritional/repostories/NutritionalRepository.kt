package pack.program.nutritional.repostories

import pack.program.nutritional.api.RetroInstance

class NutritionalRepository {

    suspend fun getBreakingNews(country: String, pageNumber: Int) =
        RetroInstance.api.getBreakingNews(country, pageNumber)

    suspend fun getNutiritionData(queries: HashMap<String, String>) =  //fields: ArrayList<String>,
        RetroInstance.api.getNutiritionData(queries) //appID, appKey, fields, queries

}