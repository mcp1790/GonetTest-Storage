package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.NetworkModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.child.NetworkRealmEntity

object NetworkModelConverter: IConverter<NetworkRealmEntity, NetworkModel> {
    override fun entityToModel(entityIn: NetworkRealmEntity?): NetworkModel {
        return entityIn?.let { entity ->
            NetworkModel(
                id = entity.id,
                logoPath = entity.logo_path,
                name = entity.name,
                originCountry = entity.origin_country,
            )
        } ?: NetworkModel()
    }

    override fun modelToEntity(modelIn: NetworkModel): NetworkRealmEntity {
        return NetworkRealmEntity().apply {
            id = modelIn.id
            logo_path = modelIn.logoPath
            name = modelIn.name
            origin_country = modelIn.originCountry
        }
    }
}