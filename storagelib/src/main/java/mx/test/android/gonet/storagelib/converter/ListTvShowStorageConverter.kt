package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.ListTvShowsModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.ListTvShowsRealmEntity
import mx.test.android.gonet.storagelib.entity.TvShowRawRealmEntity

object ListTvShowStorageConverter : IStorageConverter<ListTvShowsRealmEntity, ListTvShowsModel> {
    override fun entityToModel(entityIn: ListTvShowsRealmEntity?): ListTvShowsModel {
        return entityIn?.let { entity ->
            ListTvShowsModel(
                results = entity.results?.map {
                    TvShowRawStorageConverter.entityToModel(it)
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
                resultRealmList.add(TvShowRawStorageConverter.modelToEntity(it))
            }
            results = resultRealmList
            total_pages = modelIn.totalPages
            total_results = modelIn.totalResults
            page = modelIn.page
        }
    }
}