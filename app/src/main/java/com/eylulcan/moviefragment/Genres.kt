package com.eylulcan.moviefragment

enum class Genres (val value:Int){
    Action(28){
        override fun movieGenre(): String {
            return "Action"
        }

    },
    Adventure(12) {
        override fun movieGenre(): String {
            return "Adventure"
        }
    },
    Animation(16) {
        override fun movieGenre(): String {
            return "Animation"
        }
    },
    Comedy(35) {
        override fun movieGenre(): String {
            return "Comedy"
        }
    },
    Crime(80) {
        override fun movieGenre(): String {
            return  "Crime"
        }
    },
    Documentary(99) {
        override fun movieGenre(): String {
            return "Documentary"
        }
    },
    Drama(18) {
        override fun movieGenre(): String {
            return "Drama"
        }
    },
    Family(10751) {
        override fun movieGenre(): String {
            return "Family"
        }
    },
    Fantasy(14) {
        override fun movieGenre(): String {
            return "Fantasy"
        }
    },
    History(36) {
        override fun movieGenre(): String {
            return "History"
        }
    },
    Horror(27) {
        override fun movieGenre(): String {
            return "Horror"
        }
    },
    Music(10402) {
        override fun movieGenre(): String {
            return "Music"
        }
    },
    Mystery(9648) {
        override fun movieGenre(): String {
            return "Mystery"
        }
    },
    Romance(10749) {
        override fun movieGenre(): String {
            return "Romance"
        }
    },
    ScienceFiction(878) {
        override fun movieGenre(): String {
            return "Science Fiction"
        }
    },
    TVMovies(10770) {
        override fun movieGenre(): String {
            return "TV Movies"
        }
    },
    Thriller(53) {
        override fun movieGenre(): String {
            return "Thriller"
        }
    },
    War(10752) {
        override fun movieGenre(): String {
            return "War"
        }
    },
    Western(37) {
        override fun movieGenre(): String {
            return "Western"
        }
    };
    abstract fun movieGenre(): String


    companion object{
        fun valueOfInt(value: Int) = Genres.values().find { it.value == value }
    }
}