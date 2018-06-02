package carloscaldas.fiap.com.br.azurecatalog.repository

import carloscaldas.fiap.com.br.azurecatalog.entities.PriorityEntity

class PriorityCacheConstants private constructor(){

    companion object {

        fun setCache(list: List<PriorityEntity>){
            for (item in list){
                mPriorityCache.put(item.id, item.description)
            }
        }

        fun getPriorityDescription(id: Int) : String{
            if (mPriorityCache[id] == null){
                return ""
            }
            return mPriorityCache[id].toString()
        }

        private val mPriorityCache = hashMapOf<Int, String>()

    }

}