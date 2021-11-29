package mx.test.android.gonet.servicelib.converters

interface IStorageConverter<A,B> {
    fun entityToModel(entityIn: A?): B
    fun modelToEntity(modelIn: B): A
}