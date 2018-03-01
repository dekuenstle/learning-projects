w = love.graphics.getWidth();
h = love.graphics.getHeight();

max_acc = 180
max_vel = 60
max_visible_dist = 60
damping = 0.0
min_dist = 18;
mouse_flee_radius = 35

keep_distance = true;
stay_centered = true
adjust_velocity = true
stay_on_display = true
show_visible = false
should_flee = false
boids = {};


function love.mousereleased(x, y, button)
   if button == "l" then
      addBoid(x,y)
   end
   if button == "r" then
      remove_nearest_Boid(x,y)
   end
end

function love.keypressed( key, isrepeat )
	if (key == "d") then 
		keep_distance = not keep_distance
	end
	if (key == "c") then 
		stay_centered = not stay_centered
	end
	if (key == "v") then 
		adjust_velocity = not adjust_velocity
	end
	if (key == "s") then 
		stay_on_display = not stay_on_display
	end
	if (key == "q") then 
		show_visible = not show_visible
	end
	if (key == "t") then 
		for i = 1, 10, 1 do
			addBoid(w*0.5, h*0.5);
		end
	end
	if (key == "f") then
		should_flee = not should_flee
	end
end
function addBoid(x,y)
	local b = {
		pos_x = x, 
		pos_y = y,
		vel_x = math.random()*14-7,
		vel_y = math.random()*14-7,
		acc_x = 0,
		acc_y = 0,
	}
	table.insert(boids,b)
end

function remove_nearest_Boid(x,y)
	local distSQ = math.huge
	local i = 1
	for  index,  value in ipairs(boids)  do
		local temp = (value.pos_x-x)*(value.pos_x-x)+(value.pos_y-y)*(value.pos_y-y)
		if distSQ>temp then
			distSQ = temp
			i = index 
		end
	end
	table.remove(boids,i)
end


function dir_move_to(boid,x,y)
	local dx, dy, l
	dx = x - boid.pos_x
	dy = y - boid.pos_y
	local l  = math.sqrt(dx*dx+dy*dy)
	if (l>0.0) then
		return dx/l, dy/l
	else
		return 0,0
	end
end



function boid_apply_acc(boid, dir_x, dir_y)
	local l  = math.sqrt(dir_x*dir_x+dir_y*dir_y)
	if l<=0.0 then return end
	boid.acc_x = dir_x/l*max_acc; 	
	boid.acc_y = dir_y/l*max_acc;
end

function visible_boids(boid)
	local dsq = max_visible_dist*max_visible_dist
	local dx = 0; local dy = 0
	local visibles = {}
	for  index,  value in ipairs(boids)  do
		dx = value.pos_x - boid.pos_x
		dy = value.pos_y - boid.pos_y
		if dx*dx + dy*dy <= dsq then
			table.insert(visibles, value);
		end
	end
	return visibles
end

function dir_flee_mouse(boid)
	local mx, my = love.mouse.getPosition( )
	if ((mx-boid.pos_x)*(mx-boid.pos_x)+(my-boid.pos_y)*(my-boid.pos_y)<mouse_flee_radius*mouse_flee_radius) then
		local dx, dy = dir_move_to(boid, mx, my)
		return -dx,-dy
	else
		return 0,0
	end
	
end

function dir_keep_distance(boid, vis)
	local dir_x=0; local dir_y=0; local dx = 0; local dy = 0; 
	local l = 0
	for  index,  value in ipairs(vis)  do
		dx = value.pos_x - boid.pos_x
		dy = value.pos_y - boid.pos_y
		l  = math.sqrt(dx*dx+dy*dy)
		if l>0.0 and l <= min_dist then
			dir_x = dir_x-dx/l; dir_y = dir_y-dy/l
		end
	end
	
	l  = math.sqrt(dir_x*dir_x+dir_y*dir_y)
	if l>0.0 then
		return dir_x/l, dir_y/l
		--return dx,dy
	else
		return 0, 0
	end
end

function dir_center(boid, vis)
	local x = 0
 	local y = 0
	local c = 0
	for  index,  value in ipairs(vis)  do
		x = x+value.pos_x
		y = y+value.pos_y
		c = c+1
	end
	if c>0 then
		 x=x/c; y=y/c 
	else
		x=boid.pos_x; y = boid.pos_y 
	end
	return dir_move_to(boid, x,y)
end

function dir_average_vel(boid, vis)
	local x = 0
 	local y = 0
	local c = 0
	for  index,  value in ipairs(vis)  do
		x = x+value.vel_x
		y = y+value.vel_y
		c = c+1
	end
	if c>0 then
		 x=x/c; y=y/c 
	else
		x=boid.vel_x; y = boid.vel_y 
	end
	
	local l  = math.sqrt(x*x+y*y)
	if l>0.0 then
		return x/l, y/l
		--return dx,dy
	else
		return 0, 0
	end
end


function love.load()
	
	for i = 1, 50, 1 do
		addBoid(math.random()*w*0.4+w*0.3, math.random()*h*0.4+h*0.3);
		
		--addBoid(math.random()+w*0.5, math.random()+h*0.5);
		
	end
   
end

function love.update(dt)
	-- Physics
	for  index,  value in ipairs(boids)  do
		value.vel_x = value.vel_x+dt*value.acc_x;
		value.vel_y = value.vel_y+dt*value.acc_y;
		
		value.vel_x = value.vel_x*(1.0-damping)
		value.vel_y = value.vel_y*(1.0-damping)
		
		local vel = math.sqrt(value.vel_x*value.vel_x+value.vel_y*value.vel_y)
		if(vel>max_vel) then
			local d = max_vel/vel;
			value.vel_x = value.vel_x* d
			value.vel_y = value.vel_y*d
		end
		
		value.pos_x = value.pos_x+dt*value.vel_x;
		value.pos_y = value.pos_y+dt*value.vel_y;
	end
	
	-- Boid's Mind
	for  index,  value in ipairs(boids)  do
		--dx, dy = dir_move_to(value,w*0.5,h*0.5)
		local vis = visible_boids(value)
		
		local dx, dy = 0,0
		if keep_distance then dx, dy = dir_keep_distance(value, vis) end
		local cx, cy = 0,0
		if stay_centered then cx, cy = dir_center(value,vis) end
		local vx, vy = dir_average_vel(value,vis)
		if adjust_velocity then vx, vy = dir_average_vel(value,vis) end
		local wx=0
		local wy = 0 
		if stay_on_display then 
			local offset = 40
			if (value.pos_x<offset) then wx = 1 end
			if (value.pos_y<offset) then wy = 1 end
			if (value.pos_x>w-offset) then wx = -1 end
			if (value.pos_y>h-offset) then wy = -1 end
		end
		local fx, fy = 0,0
		if (should_flee) then
			fx, fy = dir_flee_mouse(value)
		end
		boid_apply_acc(value, (dx+wx)*2+cx+vx+8*fx,(dy+wy)*2+cy+vy+wy+8*fy)
	end
end

function love.draw()
	for  index,  value in ipairs(boids)  do
	    love.graphics.circle( "fill", value.pos_x, value.pos_y, 5, 10 )
		
		if show_visible then
	    love.graphics.circle( "line", value.pos_x, value.pos_y, max_visible_dist, 100 )
	end
	end

    love.graphics.print(string.format("Keep distance[d]:%s, Stay centered[c]:%s, Adjust velocity[v]:%s, Stay on display[s]:%s",tostring(keep_distance),tostring(stay_centered),tostring(adjust_velocity),tostring(stay_on_display)), 0, 0)
	 love.graphics.print(string.format("Left klick: create, Right click: kill, Show Visible[q]:%s, Flee[f]:%s, Create 10 Boids[t]",tostring(show_visible),tostring(should_flee)), 0, 20)
end