/***** Polyfill *****/
/* https://gist.github.com/paulirish/1579671#file-raf-js */
(function() {
    var lastTime = 0;
    var vendors = ['ms', 'moz', 'webkit', 'o'];
    for(var x = 0; x < vendors.length && !window.requestAnimationFrame; ++x) {
        window.requestAnimationFrame = window[vendors[x]+'RequestAnimationFrame'];
        window.cancelAnimationFrame = window[vendors[x]+'CancelAnimationFrame']
        || window[vendors[x]+'CancelRequestAnimationFrame'];
    }

    if (!window.requestAnimationFrame)
        window.requestAnimationFrame = function(callback, element) {
            var currTime = new Date().getTime();
            var timeToCall = Math.max(0, 16 - (currTime - lastTime));
            var id = window.setTimeout(function() { callback(currTime + timeToCall); },
                timeToCall);
            lastTime = currTime + timeToCall;
            return id;
        };

    if (!window.cancelAnimationFrame)
        window.cancelAnimationFrame = function(id) {
            clearTimeout(id);
        };
}());
/********************/


(function(){

    var WaveRenderer = function (canvas, wave) {
        var width = canvas.width
        var height = canvas.height
        var vertCenter = height / 2.0
        var context = canvas.getContext("2d")
        var shouldRender = false
        var start = null

        this.start =function(){
            shouldRender = true
            window.requestAnimationFrame( step )
        }

        this.stop = function(){
            shouldRender = false
        }

        var step = function(timestamp){
            if (!start) start = timestamp;
            var progress = timestamp - start;
            render(timestamp)
            if(shouldRender) {
                window.requestAnimationFrame(step)
            }
        }

        var render = function(progress){
            progress /= 1000
            context.clearRect ( 0 , 0 , canvas.width, canvas.height );
            context.beginPath()
            context.lineWidth = "3"
            context.strokeStyle = "red"
            var x = 1
            var y = wave.getElevation( x, progress, 200)
            context.moveTo( x, y+vertCenter )
            for ( x = 2 ; x <= width; x++){
                y = wave.getElevation( x, progress, 200)
                context.lineTo( x, y+vertCenter )
            }
            context.stroke()
        }
    }

    window.WaveRenderer = WaveRenderer

})()
