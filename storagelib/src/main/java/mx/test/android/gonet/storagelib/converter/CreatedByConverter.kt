package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.CreatedByModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.child.CreatedByRealmEntity

object CreatedByConverter: IConverter<CreatedByRealmEntity, CreatedByModel> {
    override fun entityToModel(entityIn: CreatedByRealmEntity?): CreatedByModel {
        return entityIn?.let { entity ->
            CreatedByModel(
                creditId = entity.credit_id,
                gender = entity.gender,
                id = entity.id,
                name = entity.name,
                profilePath = entity.profile_path
            )
        } ?: CreatedByModel()
    }

    override fun modelToEntity(modelIn: CreatedByModel): CreatedByRealmEntity {
        return CreatedByRealmEntity().apply {
            credit_id = modelIn.creditId
            gender = modelIn.gender
            id = modelIn.id
            name = modelIn.name
            profile_path = modelIn.profilePath
        }
    }
}