package mx.test.android.gonet.storagelib.entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import mx.test.android.gonet.storagelib.entity.child.*

open class TvShowDetailRealmEntity: RealmObject()  {
    var backdrop_path: String = ""
    var created_by: RealmList<CreatedByRealmEntity> = RealmList()
    var episode_run_time: RealmList<Int> = RealmList()
    var first_air_date: String = ""
    var genres: RealmList<GenreRealmEntity> = RealmList()
    var homepage: String = ""
    @PrimaryKey
    var id: Int = -1
    var in_production: Boolean = false
    var languages: RealmList<String> = RealmList()
    var lastA_air_date: String = ""
    var last_episode_to_air: LastEpisodeToAirRealmEntity = LastEpisodeToAirRealmEntity()
    var name: String = ""
    var networks: List<NetworkRealmEntity> = RealmList()
    var number_of_episodes: Int = -1
    var number_of_seasons: Int = -1
    var origin_country: RealmList<String> = RealmList()
    var original_language: String = ""
    var original_name: String = ""
    var overview: String = ""
    var popularity: Double = -1.0
    var poster_path: String = ""
    var production_companies: RealmList<ProductionCompanyRealmEntity> = RealmList()
    var production_countries: RealmList<ProductionCountryRealmEntity> = RealmList()
    var seasons: RealmList<SeasonRealmEntity> = RealmList()
    var spoken_languages: RealmList<SpokenLanguageRealmEntity> = RealmList()
    var status: String = ""
    var tagline: String = ""
    var type: String = ""
    var vote_average: Double = -1.0
    var vote_count: Int = -1
}