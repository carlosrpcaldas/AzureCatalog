package carloscaldas.fiap.com.br.azurecatalog.business

import android.content.Context
import carloscaldas.fiap.com.br.azurecatalog.entities.PriorityEntity
import carloscaldas.fiap.com.br.azurecatalog.repository.PriorityRepository

class PriorityBusiness (context: Context){

    private val mPriorityRepository : PriorityRepository = PriorityRepository.getInstance(context)

    fun getList(): MutableList<PriorityEntity> = mPriorityRepository.getList()

}