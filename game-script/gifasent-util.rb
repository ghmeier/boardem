#!/usr/bin/ruby

require 'rubygems'
require 'json'
require 'optparse'
require 'net/http'
require 'openssl'
require 'uri'


def getResponse(uri, params)

	http = Net::HTTP.new(uri.host,uri.port)
	http.use_ssl=false
	http.verify_mode = OpenSSL::SSL::VERIFY_NONE

	#uri.query = URI.encode_www_form(params)
	request = Net::HTTP::Get.new(uri.request_uri)
	request['Content-Type'] = 'application/json'
	request['Accept'] = 'application/json'

	response = http.request(request)
	response['Content-Type'] = 'application/json'

	#if valid_json?(response.body)
		return JSON.parse(response.body)
	#else
	#	return response.body
	#end

end
