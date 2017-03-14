local blueprintLib = require "blueprintstring/blueprintstring"
local jsonLib = require "JSON"

local args = {...}
local json = args[1]

local blueprint = jsonLib:decode(json)
local string = blueprintLib.toString(blueprint)

return string;