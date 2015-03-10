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

def putResponse(uri, data)

	http = Net::HTTP.new(uri.host,uri.port)
	http.use_ssl=true
	http.verify_mode = OpenSSL::SSL::VERIFY_NONE

	#uri.query = URI.encode_www_form(params)
	request = Net::HTTP::Put.new(uri.request_uri)
	request['Content-Type'] = 'application/json'
	request['Accept'] = 'application/json'
	request.body=data
	response = http.request(request)
	response['Content-Type'] = 'application/json'

	return JSON.parse(response.body)

end

def patchResponse(uri, data)

	http = Net::HTTP.new(uri.host,uri.port)
	http.use_ssl=true
	http.verify_mode = OpenSSL::SSL::VERIFY_NONE

	#uri.query = URI.encode_www_form(params)
	request = Net::HTTP::Patch.new(uri.request_uri)
	request['Content-Type'] = 'application/json'
	request['Accept'] = 'application/json'
	request.body=data

	response = http.request(request)
	response['Content-Type'] = 'application/json'

	return JSON.parse(response.body)

end

def deleteResponse(uri)

	http = Net::HTTP.new(uri.host,uri.port)
	http.use_ssl=true
	http.verify_mode = OpenSSL::SSL::VERIFY_NONE

	#uri.query = URI.encode_www_form(params)
	request = Net::HTTP::Delete.new(uri.request_uri)
	request['Content-Type'] = 'application/json'
	request['Accept'] = 'application/json'

	response = http.request(request)
	response['Content-Type'] = 'application/json'

end
