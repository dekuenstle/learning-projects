(function(){

    var SinusWave = function( amplitude, frequency, phaseConstant ) {
        var angularFrequency = 2 * Math.PI * frequency

        this.getAmplitude = function () {
            return amplitude
        }
        this.getFrequency = function() {
            return frequency
        }
        this.getAngularFrequency = function () {
            return angularFrequency
        }

        this.getPhaseConstant = function () {
            return phaseConstant
        }
    }

    SinusWave.prototype.getWavelength = function (phaseSpeed) {
        return phaseSpeed / this.getFrequency()
    }

    SinusWave.prototype.getWavenumber = function( phaseSpeed ) {
        return 2*Math.PI / this.getWavelength( phaseSpeed )
    }

    SinusWave.prototype.getElevation = function(pos, time, phaseSpeed){
        return this.getAmplitude() * Math.sin( this.getWavenumber(phaseSpeed) * pos - this.getAngularFrequency() * time + this.getPhaseConstant() )
    }

    window.SinusWave = SinusWave;

})()
