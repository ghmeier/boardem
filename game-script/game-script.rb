#!/usr/bin/ruby
require 'gifasent-util.rb'

require 'uri'
require 'rubygems'
require 'json'
require 'firebase'

base_uri = 'https://boardem.firebaseio.com/games/'

$i = 864
$num = 120120

while $i < 1000 do
		uri = URI("http://bgg-json.azurewebsites.net/thing/#{$i}")
		response = getResponse(uri, '')
		game_name = response['name']
		if (!game_name.nil?)
			game_name.gsub!(/[^0-9A-Za-z]/, '')
			game_uri = URI(base_uri+game_name+".json")
			game_data = JSON.dump(response)
			putResponse(game_uri,game_data)
			print "#{$i} "
		else
			sleep 10
		end
	$i+=1
	sleep 1
end
puts $i
puts "Script Finished"