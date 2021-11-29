package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.LastEpisodeToAirModel
import mx.test.android.gonet.servicelib.converters.IConverter
import mx.test.android.gonet.storagelib.entity.child.LastEpisodeToAirRealmEntity

object LastEpisodeOnAirConverter: IConverter<LastEpisodeToAirRealmEntity, LastEpisodeToAirModel> {
    override fun entityToModel(entityIn: LastEpisodeToAirRealmEntity?): LastEpisodeToAirModel {
        return entityIn?.let { entity ->
            LastEpisodeToAirModel(
                airDate = entity.air_date,
                episodeNumber = entity.episode_number,
                id = entity.id,
                name = entity.name,
                overview = entity.overview,
                productionCode = entity.production_code,
                seasonNumber = entity.season_number,
                stillPath = entity.still_path,
                voteAverage = entity.vote_average,
                voteCount = entity.vote_count
            )
        } ?: LastEpisodeToAirModel()
    }

    override fun modelToEntity(modelIn: LastEpisodeToAirModel): LastEpisodeToAirRealmEntity {
        return LastEpisodeToAirRealmEntity().apply {
            air_date = modelIn.airDate
            episode_number = modelIn.episodeNumber
            id = modelIn.id
            name = modelIn.name
            overview = modelIn.overview
            production_code = modelIn.productionCode
            season_number = modelIn.seasonNumber
            still_path = modelIn.stillPath
            vote_average = modelIn.voteAverage
            vote_count = modelIn.voteCount
        }
    }
}