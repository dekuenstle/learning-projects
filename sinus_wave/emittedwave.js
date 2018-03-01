(function(){

    var EmitableWave = function (wave) {
        var startTime, stopTime

        this.start = function( time ){
            if( typeof startTime != 'undefined') {
                throw "already emitted wave"
            }
            startTime = time
        }

        this.stop = function( time ){
            if( typeof stopTime != 'undefined'){
                throw "already stopped emission"
            }
            stopTime = time
            if( startTime > stopTime ) {
                throw "stop time has to be before start time"
            }
        }

        this.isEmittedAt = function(time){
            return typeof startTime != 'undefined' && startTime <= time && (typeof stopTime == 'undefined' || time <= stopTime)
        }

        this.getFarestPosition = function( time, phaseSpeed ){
            if( typeof startTime == 'undefined') return undefined
            if( time < startTime ) return undefined
            return phaseSpeed * ( time - startTime )
        }

        this.getNearestPosition = function( time, phaseSpeed ){
            if( ! this.isEmittedAt(time) ) return undefined
            if(  typeof stopTime == 'undefined' ) return 0
            if( time < stopTime ) return 0
            return phaseSpeed * ( time - stopTime )
        }

        this.getElevation = function(pos, time, phaseSpeed) {
            if(!this.isEmittedAt(time)) {
                return 0
            }
            var farest = this.getFarestPosition( time, phaseSpeed )
            var nearest = this.getNearestPosition( time, phaseSpeed )
            if( pos > farest || pos < nearest ){
                return 0
            }
            var progress = time - startTime
            return wave.getElevation(pos, progress, phaseSpeed)
        }
    }

    window.EmitableWave = EmitableWave
})()
