/*
********* Keyframes: **************

**** External format:
{
	from:{
		<value_name>:"<value><unit>",
		...
	},
	to:{
		<value_name>:"<value><unit>",
		...
	}
}

alternative:
{
	0%:{
		<value_name>:"<value><unit>",
		...
	},

	n%, m%, ...:{
		<value_name>:"<value><unit>",
		...
	},
	...
	100%:{
		<value_name>:"<value><unit>",
		...
	}
}

***********************************

**** Internal format:
[
	0, { <value_name>:[<value_at_0>,"<unit>"],
			...
		},
	n, { <value_name>:[<value_at_0>,"<unit>"],
			...
		},
	...
	1, { <value_name>:[<value_at_0>,"<unit>"],
			...
	}
]

***********************************

**** Example:
{
	from:{
		width:"30px"
	},
	to:{
		width:"100px",
	}
}
====>
[
	[0, {"width":[30,"px"]}]
	[1, {"width":100,"px"]}]
]


{
	0%,50%,100%:{
		width:"50px",
		height:"100px"
	},
	25%, 75%:{
		width:"100px",
		height:"50px"
	}
}
====>
[
	0, {"width":[50,"px"],"height":[100,"px"]},
	0.25, {"width":[100,"px"],"height":[50,"px"]},
	0.5, {"width":[50,"px"],"height":[100,"px"]},
	0.75, {"width":[100,"px"],"height":[50,"px"]},
	1, {"width":[50,"px"],"height":[100,"px"]}
]
*/


/* TODO: Add instructions for closure compiler */
/* TODO: Comment code */
(function() {
	/***/
	var requestAnimationFrame = (window.requestAnimationFrame ||
		window.mozRequestAnimationFrame ||
		window.webkitRequestAnimationFrame ||
		window.msRequestAnimationFrame ||
		function(func){
			window.setTimeout(
				function(){func(Date.getTime()); },
				16);
		});

	ANIM = {
		_keyframes:{},
		_animations:{},
		keyframes:function(name, keyframes){
			if( typeof name !== "string" ||
				typeof keyframes !== "object")
				return false;
			if ( !(keyframes instanceof Array)) {
				/* TODO: Parse external format */
				// keyframes = parseObjectFormat(...);
			}
			/* TODO: Validate keyframes for use as internal format */
			/* TODO: Use CSS3 whenever possible */
			ANIM._keyframes[name]=keyframes;
			return true;
		},

		/* TODO: Implement iterCount and direction */
		/* TODO: Optimize */
		animation:function(elem, name, duration, timingFunc, delay, iterCount, direction) {
			if( typeof elem !== "object" ||
				typeof name  !== "string"||
				typeof duration  !== "number"||
				typeof elem.style  !== "object" ||
				duration <= 0)
					return;
			duration *= 1000; // to milliseconds
			timingFunc = timingFunc || ANIM.linear;
			delay = delay || 0;
			iterCount = iterCount || 1;
			//direction = direction || normal;

			/* TODO: Use relative or absolute time progress??? */
			var lastTime = 0;
			var pastTime = -1000*delay;
			var keyframes = ANIM._keyframes[name];
			var nextFrameIndex = 2;
			var anim = (function(currentTime) {
				pastTime += Math.min((currentTime-lastTime), 30);
				lastTime = currentTime;
				var progress = timingFunc(
								Math.max(0.0,
									Math.min(1.0,
										pastTime/duration)));
				while(progress>keyframes[nextFrameIndex])
					nextFrameIndex+=2;

				if( progress<1.0 ) {
					var frameProgress = (progress-keyframes[nextFrameIndex-2])/
										(keyframes[nextFrameIndex]-keyframes[nextFrameIndex-2]);
					ANIM.applyFrame(
						elem,
						keyframes[nextFrameIndex-1],
						keyframes[nextFrameIndex+1],
						frameProgress);

					requestAnimationFrame(anim);
				} else {
					ANIM.applyFrame(elem, keyframes[nextFrameIndex+1],keyframes[nextFrameIndex+1],1.0);
				}


			});
			ANIM.applyFrame(elem,
						keyframes[nextFrameIndex-1],
						keyframes[nextFrameIndex-1],
						0.0);

			requestAnimationFrame(anim);
		},

		applyFrame:function(elem, last, next, intensity){
			var val;
			for (var key in last) {
				if (next[key]!==undefined &&
					last[key][1]===next[key][1]) {
					val = intensity*next[key][0]+(1-intensity)*last[key][0];
				} else {
					val = last[key][0];
				}
				/* TODO: Use vendor prefixes */
				/* TODO: Implement tranform */
				elem.style[key]=val.toString()+last[key][1];
			}
		},

		linear:function(t){
			return t;
		},
		quadraticIn:function(t){
			return t*t;
		},
		quadraticOut:function(t){
			return t*(2-t);
		},
		quadraticInOut:function(t){
			if (t<0.5) return 2*t*t;
			return t*(2.0/3.0+t-2.0/3.0*t*t);
		}
		/* TODO: Add more easing functions */
	};
})();


