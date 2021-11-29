package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.ListTvShowsRealmEntity
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity
import mx.test.android.gonet.storagelib.entity.TvShowRawRealmEntity

object ListTvShowConverter : IConverter<ListTvShowsRealmEntity, ListTvShowsModel> {
    override fun entityToModel(entityIn: ListTvShowsRealmEntity?): ListTvShowsModel {
        return entityIn?.let { entity ->
            ListTvShowsModel(
                results = entity.results?.map {
                    TvShowRawConverter.entityToModel(it)
                } ?: listOf(),
                totalPages = entity.total_pages,
                totalResults = entity.total_results,
                page = entity.page
            )
        } ?: ListTvShowsModel()
    }

    override fun modelToEntity(modelIn: ListTvShowsModel): ListTvShowsRealmEntity {
        return ListTvShowsRealmEntity().apply {
            val resultRealmList: RealmList<TvShowRawRealmEntity> = RealmList()
            modelIn.results.forEach {
                resultRealmList.add(TvShowRawConverter.modelToEntity(it))
            }
            results = resultRealmList
            total_pages = modelIn.totalPages
            total_results = modelIn.totalResults
            page = modelIn.page
        }
    }
}