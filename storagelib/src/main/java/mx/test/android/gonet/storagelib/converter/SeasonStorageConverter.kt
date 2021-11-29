package mx.test.android.gonet.storagelib.converter

import mx.test.android.gonet.domainlib.models.child.SeasonModel
import mx.test.android.gonet.servicelib.converters.IStorageConverter
import mx.test.android.gonet.storagelib.entity.child.SeasonRealmEntity

object SeasonStorageConverter: IStorageConverter<SeasonRealmEntity, SeasonModel> {
    override fun entityToModel(entityIn: SeasonRealmEntity?): SeasonModel {
        return entityIn?.let { entity ->
            SeasonModel(
                airDate = entity.air_date,
                episodeCount = entity.episode_count,
                id = entity.id,
                name = entity.name,
                overview = entity.overview,
                posterPath = entity.poster_path,
                seasonNumber = entity.season_number,
            )
        } ?: SeasonModel()
    }

    override fun modelToEntity(modelIn: SeasonModel): SeasonRealmEntity {
        return SeasonRealmEntity().apply {
            air_date = modelIn.airDate
            episode_count = modelIn.episodeCount
            id = modelIn.id
            name = modelIn.name
            overview = modelIn.overview
            poster_path = modelIn.posterPath
            season_number = modelIn.seasonNumber
        }
    }
}