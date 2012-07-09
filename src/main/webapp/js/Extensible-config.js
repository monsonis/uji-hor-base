Extensible = {
    version: '1.5.1'
};

Extensible.Config = {
    defaults: {
        mode: 'debug',
        extJsRoot: 'http://devel.uji.es/resources/js/extjs-4.1.0/',
        extensibleRoot: '/hor/extensible-1.5.1/',
        cacheExtensible: true
    },
    
    init: function() {
        var me = this,
            config = window.ExtensibleDefaults || {};
        
        me.isIE = /msie/.test(navigator.userAgent.toLowerCase());
        
        me.mode = config.mode || me.defaults.mode;
        me.extJsRoot = config.extJsRoot || me.defaults.extJsRoot;
        me.extensibleRoot = config.extensibleRoot || me.defaults.extensibleRoot || me.getSdkPath();
        me.cacheExtensible = config.cacheExtensible || me.defaults.cacheExtensible;
        
        me.adjustPaths();
        me.writeIncludes();
    },
    
    // private -- returns the current url to this script file, which is shipped in the SDK root folder
    getSdkPath: function() {
        var scripts = document.getElementsByTagName('script'),
            thisScriptSrc = scripts[scripts.length - 1].src,
            sdkPath = thisScriptSrc.substring(0, thisScriptSrc.lastIndexOf('/') + 1);
        
        return sdkPath;
    },
    
    // private -- helper function for ease of deployment
    adjustPaths: function() {
        if (this.extensibleRoot.indexOf('ext.ensible.com') > -1) {
            // If hosted at ext.ensible.com force non-debug release build includes
            this.mode = 'release';
        }
    },
    
    includeStylesheet: function(filePath) {
        document.write('<link rel="stylesheet" type="text/css" href="' + filePath + '" />');
    },
    
    includeScript: function(filePath) {
        document.write('<script type="text/javascript" src="' + filePath + '"></script>');
    },
    
    // private -- write out the CSS and script includes to the document
    writeIncludes: function() {
        var me = this,
            cacheBuster = '?_dc=' + (me.cacheExtensible ? Extensible.version : (+new Date)),
            suffix = '',
            bootstrap = '';
        
        switch (me.mode) {
            case 'debug':
                suffix = '-all-debug';
                break;
            
            case 'release':
                suffix = '-all';
                // For release we want to refresh the cache on first load, but allow caching
                // after that, so use the version number instead of a unique string
                cacheBuster = '?_dc=' + Extensible.version;
                break;
            
            default:
                // IE does not work in dynamic mode for the Extensible examples currently
                // based on how it (mis)handles loading of scripts when mixing includes
                // and in-page scripts. Make sure IE always uses the regular debug versions.
                if (me.isIE) {
                    suffix = '-all-debug';
                }
                else {
                    bootstrap = '-bootstrap';
                }
        }
        
        me.includeStylesheet(me.extJsRoot + 'resources/css/ext-all.css');
        me.includeStylesheet(me.extensibleRoot + 'resources/css/extensible-all.css' + cacheBuster);
       
        me.includeScript(me.extJsRoot + 'ext' + suffix + '.js');
        me.includeScript(me.extensibleRoot + 'lib/extensible' + suffix + bootstrap + '.js' + cacheBuster);
    }
};

Extensible.Config.init();

try {
    delete window.ExtensibleDefaults;
}
catch(ex) {
    window.ExtensibleDefaults = null;
}
