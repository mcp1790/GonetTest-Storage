package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity

object ListMoviesStorageConverter : IStorageConverter<ListMoviesRealmEntity, ListMoviesModel> {
    override fun entityToModel(entityIn: ListMoviesRealmEntity?): ListMoviesModel {
        return entityIn?.let { entity ->
            ListMoviesModel(
                results = entity.results?.map { MovieRawStorageConverter.entityToModel(it) }!!,
                totalPages = entity.total_pages,
                totalResults = entity.total_results,
                page = entity.page,
                dates = DatesStorageConverter.entityToModel(entity.dates)
            )
        } ?: ListMoviesModel()
    }

    override fun modelToEntity(modelIn: ListMoviesModel): ListMoviesRealmEntity {
        return ListMoviesRealmEntity().apply {
            val resultRealmList: RealmList<MovieRawRealmEntity> = RealmList()
            modelIn.results.forEach {
                resultRealmList.add(MovieRawStorageConverter.modelToEntity(it))
            }
            results = resultRealmList
            total_pages = modelIn.totalPages
            total_results = modelIn.totalResults
            page = modelIn.page
            dates = DatesStorageConverter.modelToEntity(modelIn.dates)
        }
    }
}