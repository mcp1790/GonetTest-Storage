package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.DatesModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.child.DatesRealmEntity

object DatesStorageConverter: IStorageConverter<DatesRealmEntity, DatesModel> {
    override fun entityToModel(entityIn: DatesRealmEntity?): DatesModel {
        return entityIn?.let {
            DatesModel(
                maximum = it.maximum,
                minimum = it.minimum
            )
        } ?: DatesModel()
    }

    override fun modelToEntity(modelIn: DatesModel): DatesRealmEntity {
        return DatesRealmEntity().apply {
            maximum = modelIn.maximum
            minimum = modelIn.minimum
        }
    }
}