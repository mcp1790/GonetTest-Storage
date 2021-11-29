package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.ProductionCountryModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.child.ProductionCountryRealmEntity

object ProductionCountryStorageConverter:
    IStorageConverter<ProductionCountryRealmEntity, ProductionCountryModel> {
    override fun entityToModel(entityIn: ProductionCountryRealmEntity?): ProductionCountryModel {
        return entityIn?.let { entity->
            ProductionCountryModel(
                iso31661 = entity.iso_3166_1,
                name = entity.name
            )
        } ?: ProductionCountryModel()
    }

    override fun modelToEntity(modelIn: ProductionCountryModel): ProductionCountryRealmEntity {
        return ProductionCountryRealmEntity().apply {
            iso_3166_1 = modelIn.iso31661
            name = modelIn.name
        }
    }
}