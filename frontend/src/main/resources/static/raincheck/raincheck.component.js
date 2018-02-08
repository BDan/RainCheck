'use strict';

angular.
  module('raincheck').
  component('raincheck', {
    templateUrl: 'raincheck/raincheck.template.html',
    controller: function AnswerListController($http) {
      var self = this;
      self.showLocations = false;
      
      self.locationSearch = () => {
          var query = self.location_query;
          if (!query){
              self.showLocations = false;
              return;
          }
          self.showLocations = true;
          $http.get('/api/search/locations/'+query).then(function(response) {
            self.locations = response.data;
            //console.log(self.locations);
          });
      }
      self.fetchWeather = () => {
          self.showLocations = false;
          $http.get('/api/locations/'+self.selection.key+'/conditions').then(function(response) {
            self.weather = response.data;
            //console.log(self.locations);
          });
          $http.get('/api/locations/'+self.selection.key+'/forecast').then(function(response) {
            self.forecast = response.data;
            //console.log(self.locations);
          });
      }      
    }
  });