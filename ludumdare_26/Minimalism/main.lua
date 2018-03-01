sight_canvas = {}

tile_wide = 0
map_width = 0
map_height = 0

wall_map = {}
player = {}
item_map = {}

current_level = 0

max_level = 10
goal = {}

ambient_light = 5
player_light_range = 3.5
player_light_intensity = 180
function love.load()
	love.window.setMode( 640, 480, {fullscreen=false, vsync=true} )
        love.keyboard.setKeyRepeat(0.01, 0.1)
	love.window.setTitle( "Dark Wayne" )

	sight_canvas = love.graphics.newCanvas()

	load_next_level()

end

function load_next_level()
	current_level = current_level+1
	load_level( love.image.newImageData( "level_"..current_level..".png" ) )
end

function load_level(img_data)
	wall_map = {}
	item_map = {}
	player = {}
	
	map_width = img_data:getWidth()
	map_height = img_data:getHeight()
	
	for x=0, map_width-1 do
		wall_map[x] = {}
		item_map[x] = {}
		for y=0, map_height-1 do
		
			r,g,b,a = img_data:getPixel(x,y)
			
			if (r+g+b)==0 then
			-- wall
				wall_map[x][y] = -1
			elseif r==128 and g==128 and b==128 then
			-- dont change floor
				wall_map[x][y] = 0
			else
			-- floor
				wall_map[x][y] = 1
			end
			
			
			
			
			
			if (r+b)==0 and g==128 then
				player = {x=x, y=y, color={r,g,b,255} }
			end
			
			if (r+b)==0 and g==255 then
				item_map[x][y] = {x=x, y=y, color={r,g,b,255}, item_type="goal" }
				goal = item_map[x][y]
			end
			
			
			if r==255 and g==255 and b==0 then
				item_map[x][y] = {x=x, y=y, color={r,g,b,255}, item_type="switch" }
			end
			
			
		end
	end
	
	tile_wide = love.graphics.getWidth()/map_width
	-- tile_wide = 32
end


function love.update(dt)
end

function love.keypressed(key)
	old = {x=player.x, y=player.y}
	local speed = 1
    	if key == "left" or key == "a" then 
		player.x = player.x - speed
        elseif key == "right" or key == "d" then 
		player.x = player.x + speed
    	elseif key == "up" or key == "w" then 
		player.y = player.y - speed
    	elseif key == "down" or key == "s" then
		player.y = player.y + speed
    	end
    	if( isWall(player.x, player.y )) then
    		player.x = old.x
    		player.y = old.y
    	else
    		local item = item_map[math.floor(player.x)][math.floor(player.y)]
    		if(item) then
			player_on_item(item)
    	end
    end
end

function isWall(x,y)
	if  x>=0 and y>=0 and x<map_width and y<map_height then
		if wall_map[x][y]==-1 or wall_map[x][y]==0  then
			return true
		else
			return false
		end	
	else
		return true
	end
end


function player_on_item(i)
	if i.item_type == "goal" then
		if current_level < max_level then
		load_next_level()
		end
	elseif i.item_type == "switch" then
		for x=0, map_width-1 do
		for y=0, map_height-1 do
			if  wall_map[x][y]==-1  then
				wall_map[x][y] = 1
			elseif not item_map[x][y] and wall_map[x][y]==1   then
				wall_map[x][y] = -1
			end
			
		end
	end
	end
end


function love.draw()
	love.graphics.setBackgroundColor( 255, 255, 255 )

	draw_map()
	draw_entity(player)
	
	for x=0, map_width-1 do
		for y=0, map_height-1 do
			if (item_map[x][y]) then
				draw_item(item_map[x][y])
			end
		end
	end
	draw_sight_map()
end

function draw_sight_map()
	love.graphics.setCanvas( sight_canvas )
	love.graphics.clear()

	love.graphics.setColor( 255,255,255, 255 )
	love.graphics.rectangle("fill", 0, 0, love.graphics.getWidth(), love.graphics.getHeight())

	
	love.graphics.setColor( 0,0,0, player_light_intensity*0.25)
	love.graphics.circle("fill", 
		             player.x*tile_wide+tile_wide*0.5,
			     player.y*tile_wide+tile_wide*0.5,
			     tile_wide*player_light_range )
	love.graphics.circle("fill",
                             goal.x*tile_wide+tile_wide*0.5,
                             goal.y*tile_wide+tile_wide*0.5,
                             tile_wide*0.8 )

	love.graphics.setColor( 0,0,0, player_light_intensity*0.5 )
	love.graphics.circle( "fill", player.x*tile_wide+tile_wide*0.5, player.y*tile_wide+tile_wide*0.5, tile_wide*player_light_range*0.6 )

	
	love.graphics.setColor( 0,0,0, player_light_intensity )
	love.graphics.circle( "fill", player.x*tile_wide+tile_wide*0.5, 
player.y*tile_wide+tile_wide*0.5, tile_wide*player_light_range*0.3 )
	
	
	love.graphics.setCanvas(  )
	love.graphics.setBlendMode("subtract")
	love.graphics.setColor( 255,255,255, 255-ambient_light )
	love.graphics.draw( sight_canvas )
	love.graphics.setBlendMode("alpha")
end


function draw_entity(e)
	love.graphics.setColor( e.color )
	local offset = tile_wide*0.1;
	local wide = tile_wide-2*offset;

	love.graphics.rectangle( "fill", e.x*tile_wide+offset, e.y*tile_wide+offset, wide, wide )

end

function draw_item(i)
	love.graphics.setColor( i.color )
	local half = tile_wide*0.5;
	local quarter = half*0.5;
	love.graphics.circle( "fill", i.x*tile_wide+half, i.y*tile_wide+half, half*0.8, 20 )

end

function draw_map()
	love.graphics.setColor( 0, 0, 0 )
	for x=0, table.getn(wall_map), 1 do
		for y=0, table.getn(wall_map[x]), 1 do
			if( wall_map[x][y]==-1 ) or wall_map[x][y]==0 then
				love.graphics.rectangle( "fill", x*tile_wide, y*tile_wide, tile_wide, tile_wide )
			end
		end
	end
end

