'use strict';

angular.
module('raincheck').
component('raincheck', {
    templateUrl: 'raincheck/raincheck.template.html',
    controller: function AnswerListController($http) {
        var self = this;
        self.showLocations = false;
        self.showFavorites = false;
        self.showWeather = false;

        self.locationSearch = () => {
            var query = self.location_query;
            if (!query) {
                self.showLocations = false;
                return;
            }
            self.showLocations = true;
            self.showFavorites = false;
            self.showWeather = false;            
            $http.get('/api/search/locations/' + query).then(function (response) {
                self.locations = response.data;
            }, function (err) {
                self.locations = {};
                self.locations.success = false;
                self.locations.error = err.data;
            });
        }
        self.fetchWeather = () => {
            self.showLocations = false;
            self.showFavorites = false;
            self.showWeather = true;
            
            $http.get('/api/locations/' + self.selection.key + '/conditions').then(function (response) {
                self.weather = response.data;
            });
            $http.get('/api/locations/' + self.selection.key + '/forecast').then(function (response) {
                self.forecast = response.data;
            });
        };
        self.fetchFavorites = () => {
            self.showLocations = false;
            self.showFavorites = true;
            self.showWeather = false;
            $http.get('/data/favorites').then(function (response) {
                self.favorites = response.data._embedded;
            });
        };
        self.addFavorite = (name, key) => {
            $http.post('/data/favorites', {
                    name: name,
                    locationKey: key,
                    user: "#"
                })
                .then(response => {
                        self.fetchFavorites()
                    },
                    err => {
                        alert(err.data.message)
                    });
        }
        self.removeFavorite = (url) => {
            console.log("delete: " + url);
            $http({
                    method: 'DELETE',
                    url: url,
                    headers: {
                        'Content-type': 'application/json;charset=utf-8'
                    }
                })
                .then(response => {
                        self.fetchFavorites()
                    },
                    err => {
                    	alert(err.data.message)
                    });
        }
        self.onKeypressedLocation = (event) => {
            if (event.keyCode === 13) {
                self.locationSearch();

            }

        };
        self.doBlur = $event => {
            $event.target.blur();
        }
        //self.fetchFavorites();
    }
});
