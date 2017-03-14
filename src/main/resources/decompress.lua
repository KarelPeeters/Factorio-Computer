local blueprintLib = require "blueprintstring/blueprintstring"
local jsonLib = require "JSON"

local args = {...}
local string = args[1]

local blueprint = blueprintLib.fromString(string)
local json = jsonLib:encode(blueprint)

return json;