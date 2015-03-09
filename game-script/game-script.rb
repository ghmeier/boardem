#!/usr/bin/ruby
load 'gifasent-util.rb'

require 'uri'
require 'rubygems'
require 'json'
require 'firebase'

base_uri = 'https://boardem.firebaseio.com/'
firebase = Firebase::Client.new(base_uri)

$i = 1
$num = 120120
number_entries = 5000
begin
	if number_entries < 5000
		uri = URI("http://bgg-json.azurewebsites.net/thing/"+ $i)
		response = getResponse(uri, '')

		game_id = response['gameId']
		game_name = response['name']
		game_description = response['description']
		game_image = response['thumbnail']
		game_players = response['minPlayers'] + " - " + response['maxPlayers']
		game_playingtime = response['playingTime']
		game_rating = response['averageRating']

		if game_rating > 7 
			firebase_response = firebase.push(game_name, { :id => game_id})
			puts firebase_response.success?
			number_entries += 1
		end
	end
	$i+=1
end

puts "Script Finished"