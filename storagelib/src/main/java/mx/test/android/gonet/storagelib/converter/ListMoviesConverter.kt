package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.ListMoviesModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.ListMoviesRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity

object ListMoviesConverter : IConverter<ListMoviesRealmEntity, ListMoviesModel> {
    override fun entityToModel(entityIn: ListMoviesRealmEntity?): ListMoviesModel {
        return entityIn?.let { entity ->
            ListMoviesModel(
                results = entity.results?.map { MovieRawConverter.entityToModel(it) }!!,
                totalPages = entity.total_pages,
                totalResults = entity.total_results,
                page = entity.page,
                dates = DatesConverter.entityToModel(entity.dates)
            )
        } ?: ListMoviesModel()
    }

    override fun modelToEntity(modelIn: ListMoviesModel): ListMoviesRealmEntity {
        return ListMoviesRealmEntity().apply {
            val resultRealmList: RealmList<MovieRawRealmEntity> = RealmList()
            modelIn.results.forEach {
                resultRealmList.add(MovieRawConverter.modelToEntity(it))
            }
            results = resultRealmList
            total_pages = modelIn.totalPages
            total_results = modelIn.totalResults
            page = modelIn.page
            dates = DatesConverter.modelToEntity(modelIn.dates)
        }
    }
}