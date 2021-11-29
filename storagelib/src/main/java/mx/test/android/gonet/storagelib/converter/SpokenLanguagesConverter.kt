package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.SpokenLanguageModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.child.SpokenLanguageRealmEntity

object SpokenLanguagesConverter : IConverter<SpokenLanguageRealmEntity, SpokenLanguageModel> {
    override fun entityToModel(entityIn: SpokenLanguageRealmEntity?): SpokenLanguageModel {
        return entityIn?.let { entity ->
            SpokenLanguageModel(
                englishName = entity.english_name,
                iso6391 = entity.iso_639_1,
                name = entity.name
            )
        } ?: SpokenLanguageModel()
    }

    override fun modelToEntity(modelIn: SpokenLanguageModel): SpokenLanguageRealmEntity {
        return SpokenLanguageRealmEntity().apply {
            english_name = modelIn.englishName
            iso_639_1 = modelIn.iso6391
            name = modelIn.name
        }
    }
}