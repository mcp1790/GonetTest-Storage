package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.GenreModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity

object GenreConverter: IConverter<GenreRealmEntity, GenreModel> {
    override fun entityToModel(entityIn: GenreRealmEntity?): GenreModel {
        return entityIn?.let {
            GenreModel(
                id = it.id,
                name = it.name
            )
        } ?: GenreModel()
    }

    override fun modelToEntity(modelIn: GenreModel): GenreRealmEntity {
        return GenreRealmEntity().apply {
            id = modelIn.id
            name = modelIn.name
        }
    }
}