package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.TvShowRawModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.TvShowRawRealmEntity
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity

object TvShowRawStorageConverter: IStorageConverter<TvShowRawRealmEntity, TvShowRawModel> {
    override fun entityToModel(entityIn: TvShowRawRealmEntity?): TvShowRawModel {
        return entityIn?.let { entity ->
            TvShowRawModel(
                backdropPath = entity.backdrop_path,
                firstAirDate = entity.first_air_date,
                genres = entity.genres.map { GenreStorageConverter.entityToModel(it) },
                id = entity.id,
                name = entity.name,
                originCountry = entity.origin_country,
                originalLanguage = entity.original_language,
                originalName = entity.original_name,
                overview = entity.overview,
                popularity = entity.popularity,
                posterPath = entity.poster_path,
                voteAverage = entity.vote_average,
                voteCount = entity.vote_count,
            )
        } ?: TvShowRawModel()
    }

    override fun modelToEntity(modelIn: TvShowRawModel): TvShowRawRealmEntity {
        return TvShowRawRealmEntity().apply {
            backdrop_path = modelIn.backdropPath
            first_air_date = modelIn.firstAirDate
            val genreRealmList: RealmList<GenreRealmEntity> = RealmList()
            modelIn.genres.forEach {
                genreRealmList.add(GenreStorageConverter.modelToEntity(it))
            }
            genres = genreRealmList
            id = modelIn.id
            name = modelIn.name
            val countryRuntimeRealmList: RealmList<String> = RealmList()
            modelIn.originCountry.forEach {
                countryRuntimeRealmList.add(it)
            }
            origin_country = countryRuntimeRealmList
            original_language = modelIn.originalLanguage
            original_name = modelIn.originalName
            overview = modelIn.overview
            popularity = modelIn.popularity
            poster_path = modelIn.posterPath
            vote_average = modelIn.voteAverage
            vote_count = modelIn.voteCount
        }
    }
}