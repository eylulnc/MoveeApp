package com.eylulcan.moviefragment

enum class Genres(val value: Int) {
    Action(28) {
        override fun movieGenreName(): String {
            return "Action"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.action_image
        }
    },
    Adventure(12) {
        override fun movieGenreName(): String {
            return "Adventure"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.adventure_image
        }
    },
    Animation(16) {
        override fun movieGenreName(): String {
            return "Animation"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.animation_image
        }
    },
    Comedy(35) {
        override fun movieGenreName(): String {
            return "Comedy"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.comedy_image
        }
    },
    Crime(80) {
        override fun movieGenreName(): String {
            return "Crime"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.crime_image
        }
    },
    Documentary(99) {
        override fun movieGenreName(): String {
            return "Documentary"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.documentary_image
        }
    },
    Drama(18) {
        override fun movieGenreName(): String {
            return "Drama"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.drama_image
        }
    },
    Family(10751) {
        override fun movieGenreName(): String {
            return "Family"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.family_image
        }
    },
    Fantasy(14) {
        override fun movieGenreName(): String {
            return "Fantasy"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.fantasy_image
        }
    },
    History(36) {
        override fun movieGenreName(): String {
            return "History"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.history_image
        }
    },
    Horror(27) {
        override fun movieGenreName(): String {
            return "Horror"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.horror_image
        }
    },
    Music(10402) {
        override fun movieGenreName(): String {
            return "Music"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.music_image
        }
    },
    Mystery(9648) {
        override fun movieGenreName(): String {
            return "Mystery"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.mystery_image
        }
    },
    Romance(10749) {
        override fun movieGenreName(): String {
            return "Romance"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.romance_image
        }
    },
    ScienceFiction(878) {
        override fun movieGenreName(): String {
            return "Science Fiction"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.sciencefiction_image
        }
    },
    TVMovies(10770) {
        override fun movieGenreName(): String {
            return "TV Movies"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.tvmovie_image
        }
    },
    Thriller(53) {
        override fun movieGenreName(): String {
            return "Thriller"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.thriller_image
        }
    },
    War(10752) {
        override fun movieGenreName(): String {
            return "War"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.war_image
        }
    },
    Western(37) {
        override fun movieGenreName(): String {
            return "Western"
        }

        override fun movieGenreImage(): Int {
            return R.drawable.western_image
        }
    };

    abstract fun movieGenreName(): String
    abstract fun movieGenreImage(): Int


    companion object {
        fun valueOfInt(value: Int) = Genres.values().find { it.value == value }
    }
}