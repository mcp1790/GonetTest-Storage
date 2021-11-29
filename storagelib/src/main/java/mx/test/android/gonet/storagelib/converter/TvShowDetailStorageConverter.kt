package mx.test.android.gonet.storagelib.converter

import io.realm.RealmList
import mx.test.android.gonet.domainlib.models.TvShowDetailModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.TvShowDetailRealmEntity
import mx.test.android.gonet.storagelib.entity.child.*

object TvShowDetailStorageConverter: IStorageConverter<TvShowDetailRealmEntity, TvShowDetailModel> {
    override fun entityToModel(entityIn: TvShowDetailRealmEntity?): TvShowDetailModel {
        return entityIn?.let { entity ->
            TvShowDetailModel(
                backdropPath = entity.backdrop_path,
                createdBy = entity.created_by.map { CreatedByStorageConverter.entityToModel(it) },
                episodeRunTime = entity.episode_run_time.map { it },
                firstAirDate = entity.first_air_date,
                genres = entity.genres.map { GenreStorageConverter.entityToModel(it) },
                homepage = entity.homepage,
                id = entity.id,
                inProduction = entity.in_production,
                languages = entity.languages.map { it },
                lastAirDate = entity.lastA_air_date,
                lastEpisodeToAir = LastEpisodeOnAirStorageConverter.entityToModel(entity.last_episode_to_air),
                name = entity.name,
                networks = entity.networks.map { NetworkStorageConverter.entityToModel(it) },
                numberOfEpisodes = entity.number_of_episodes,
                numberOfSeasons = entity.number_of_seasons,
                originCountry = entity.origin_country.map { it },
                originalLanguage = entity.original_language,
                originalName = entity.original_name,
                overview = entity.overview,
                popularity = entity.popularity,
                posterPath = entity.poster_path,
                productionCompanies = entity.production_companies.map { ProductionCompanyStorageConverter.entityToModel(it) },
                productionCountries = entity.production_countries.map { ProductionCountryStorageConverter.entityToModel(it) },
                seasons = entity.seasons.map { SeasonStorageConverter.entityToModel(it) },
                spokenLanguages = entity.spoken_languages.map { SpokenLanguagesStorageConverter.entityToModel(it) },
                status = entity.status,
                tagline = entity.tagline,
                type = entity.type,
                voteAverage = entity.vote_average,
                voteCount = entity.vote_count
            )
        } ?: TvShowDetailModel()
    }

    override fun modelToEntity(modelIn: TvShowDetailModel): TvShowDetailRealmEntity {
        return TvShowDetailRealmEntity().apply {
            backdrop_path = modelIn.backdropPath
            val createdRealmList: RealmList<CreatedByRealmEntity> = RealmList()
            modelIn.createdBy.forEach {
                createdRealmList.add(CreatedByStorageConverter.modelToEntity(it))
            }
            created_by = createdRealmList
            val episodeRuntimeRealmList: RealmList<Int> = RealmList()
            modelIn.episodeRunTime.forEach {
                episodeRuntimeRealmList.add(it)
            }
            episode_run_time = episodeRuntimeRealmList
            first_air_date = modelIn.firstAirDate
            val genreRealmList: RealmList<GenreRealmEntity> = RealmList()
            modelIn.genres.forEach {
                genreRealmList.add(GenreStorageConverter.modelToEntity(it))
            }
            genres = genreRealmList
            homepage = modelIn.homepage
            id = modelIn.id
            in_production = modelIn.inProduction
            val languagesRuntimeRealmList: RealmList<String> = RealmList()
            modelIn.languages.forEach {
                languagesRuntimeRealmList.add(it)
            }
            languages = languagesRuntimeRealmList
            lastA_air_date = modelIn.lastAirDate
            last_episode_to_air = LastEpisodeOnAirStorageConverter.modelToEntity(modelIn.lastEpisodeToAir)
            name = modelIn.name
            val networksRuntimeRealmList: RealmList<NetworkRealmEntity> = RealmList()
            modelIn.networks.forEach {
                networksRuntimeRealmList.add(NetworkStorageConverter.modelToEntity(it))
            }
            networks = networksRuntimeRealmList
            number_of_episodes = modelIn.numberOfEpisodes
            number_of_seasons = modelIn.numberOfSeasons
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
            val seasonRealmList: RealmList<SeasonRealmEntity> = RealmList()
            modelIn.seasons.forEach {
                seasonRealmList.add(SeasonStorageConverter.modelToEntity(it))
            }
            seasons = seasonRealmList
            val languagesRealmList: RealmList<SpokenLanguageRealmEntity> = RealmList()
            modelIn.spokenLanguages.forEach {
                languagesRealmList.add(SpokenLanguagesStorageConverter.modelToEntity(it))
            }
            spoken_languages = languagesRealmList
            status = modelIn.status
            tagline = modelIn.tagline
            type = modelIn.type
            vote_average = modelIn.voteAverage
            vote_count= modelIn.voteCount
        }
    }
}