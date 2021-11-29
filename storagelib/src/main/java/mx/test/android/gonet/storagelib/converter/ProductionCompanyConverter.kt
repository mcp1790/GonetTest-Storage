package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.ProductionCompanyModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.child.ProductionCompanyRealmEntity

object ProductionCompanyConverter: IConverter<ProductionCompanyRealmEntity, ProductionCompanyModel> {
    override fun entityToModel(entityIn: ProductionCompanyRealmEntity?): ProductionCompanyModel {
        return entityIn?.let { entity->
            ProductionCompanyModel(
                id = entity.id,
                logoPath = entity.logo_path,
                name = entity.name,
                originCountry = entity.origin_country
            )
        } ?: ProductionCompanyModel()
    }

    override fun modelToEntity(modelIn: ProductionCompanyModel): ProductionCompanyRealmEntity {
        return ProductionCompanyRealmEntity().apply {
            id = modelIn.id
            logo_path = modelIn.logoPath
            name = modelIn.name
            origin_country = modelIn.originCountry
        }
    }
}