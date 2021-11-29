package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.MovieRawModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.MovieRawRealmEntity
import mx.test.android.gonet.storagelib.entity.child.GenreRealmEntity
import mx.test.android.gonet.storagelib.entity.child.ProductionCompanyRealmEntity
import mx.test.android.gonet.storagelib.entity.child.ProductionCountryRealmEntity
import mx.test.android.gonet.storagelib.entity.child.SpokenLanguageRealmEntity

object MovieRawStorageConverter : IStorageConverter<MovieRawRealmEntity, MovieRawModel> {
    override fun entityToModel(entityIn: MovieRawRealmEntity?): MovieRawModel {
        return entityIn?.let { entity ->
            MovieRawModel(
                adult = entity.adult,
                backdropPath = entity.backdrop_path,
                budget = entity.budget,
                genres = entity.genres.map { GenreStorageConverter.entityToModel(it) },
                homepage = entity.homepage,
                id = entity.id,
                imdbId = entity.imdb_id,
                originalLanguage = entity.original_language,
                originalTitle = entity.original_title,
                overview = entity.overview,
                popularity = entity.popularity,
                posterPath = entity.poster_path,
                productionCompanies = entity.production_companies.map {
                    ProductionCompanyStorageConverter.entityToModel(it)
                },
                productionCountries = entity.production_countries.map {
                    ProductionCountryStorageConverter.entityToModel(it)
                },
                releaseDate = entity.release_date,
                revenue = entity.revenue,
                runtime = entity.runtime,
                spokenLanguages = entity.spoken_languages.map {
                    SpokenLanguagesStorageConverter.entityToModel(it)
                },
                status = entity.status,
                tagline = entity.tagline,
                title = entity.title,
                video = entity.video,
                voteAverage = entity.vote_average,
                voteCount = entity.vote_count
            )
        } ?: MovieRawModel()
    }

    override fun modelToEntity(modelIn: MovieRawModel): MovieRawRealmEntity {
        return MovieRawRealmEntity().apply {
            adult = modelIn.adult
            backdrop_path = modelIn.backdropPath
            budget = modelIn.budget
            val genreRealmList: RealmList<GenreRealmEntity> = RealmList()
            modelIn.genres.forEach {
                genreRealmList.add(GenreStorageConverter.modelToEntity(it))
            }
            genres = genreRealmList
            homepage = modelIn.homepage
            id = modelIn.id
            imdb_id = modelIn.imdbId
            original_language = modelIn.originalLanguage
            original_title = modelIn.originalTitle
            overview = modelIn.overview
            popularity = modelIn.popularity
            poster_path = modelIn.posterPath
            val companiesRealmList: RealmList<ProductionCompanyRealmEntity> = RealmList()
            modelIn.productionCompanies.forEach {
                companiesRealmList.add(ProductionCompanyStorageConverter.modelToEntity(it))
            }
            production_companies = companiesRealmList
            val countriesRealmList: RealmList<ProductionCountryRealmEntity> = RealmList()
            modelIn.productionCountries.forEach {
                countriesRealmList.add(ProductionCountryStorageConverter.modelToEntity(it))
            }
            production_countries = countriesRealmList
            release_date = modelIn.releaseDate
            revenue = modelIn.revenue
            runtime = modelIn.runtime
            val languagesRealmList: RealmList<SpokenLanguageRealmEntity> = RealmList()
            modelIn.spokenLanguages.forEach {
                languagesRealmList.add(SpokenLanguagesStorageConverter.modelToEntity(it))
            }
            spoken_languages = languagesRealmList
            status = modelIn.status
            tagline = modelIn.tagline
            title = modelIn.title
            video = modelIn.video
            vote_average = modelIn.voteAverage
            vote_count = modelIn.voteCount
        }
    }
}