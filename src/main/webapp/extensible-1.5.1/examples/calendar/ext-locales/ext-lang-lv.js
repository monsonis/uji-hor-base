/*!
 * Extensible 1.5.1
 * Copyright(c) 2010-2012 Extensible, LLC
 * licensing@ext.ensible.com
 * http://ext.ensible.com
 */
/**
 * Latvian Translations
 * By salix 17 April 2007
 */
Ext.onReady(function() {
    var cm = Ext.ClassManager, 
        exists = Ext.Function.bind(cm.get, cm);

    if(Ext.Updater){
        Ext.Updater.defaults.indicatorText = '<div class="loading-indicator">Notiek ielāde...</div>';
    }
    if(exists('Ext.view.View')){
        Ext.view.View.prototype.emptyText = "";
    }

    if(exists('Ext.grid.Panel')){
        Ext.grid.Panel.prototype.ddText = "{0} iezīmētu rindu";
    }

    if(Ext.TabPanelItem){
        Ext.TabPanelItem.prototype.closeText = "Aizver šo zīmni";
    }

    if(exists('Ext.form.field.Base')){
        Ext.form.field.Base.prototype.invalidText = "Vērtība šajā laukā nav pareiza";
    }

    if(Ext.LoadMask){
        Ext.LoadMask.prototype.msg = "Ielādē...";
    }

    if(Ext.Date) {
        Ext.Date.monthNames = [
        "Janvāris",
        "Februāris",
        "Marts",
        "Aprīlis",
        "Maijs",
        "Jūnijs",
        "Jūlijs",
        "Augusts",
        "Septembris",
        "Oktobris",
        "Novembris",
        "Decembris"
        ];

        Ext.Date.dayNames = [
        "Svētdiena",
        "Pirmdiena",
        "Otrdiena",
        "Trešdiena",
        "Ceturtdiena",
        "Piektdiena",
        "Sestdiena"
        ];
    }

    if(Ext.MessageBox){
        Ext.MessageBox.buttonText = {
            ok     : "Labi",
            cancel : "Atcelt",
            yes    : "Jā",
            no     : "Nē"
        };
    }

    if(exists('Ext.util.Format')){
        Ext.apply(Ext.util.Format, {
            thousandSeparator: '.',
            decimalSeparator: ',',
            currencySign: 'Ls',  // Latvian Lati
            dateFormat: 'd.m.Y'
        });
    }

    if(exists('Ext.picker.Date')){
        Ext.apply(Ext.picker.Date.prototype, {
            todayText         : "Šodiena",
            minText           : "Norādītais datums ir mazāks par minimālo datumu",
            maxText           : "Norādītais datums ir lielāks par maksimālo datumu",
            disabledDaysText  : "",
            disabledDatesText : "",
            monthNames	: Ext.Date.monthNames,
            dayNames		: Ext.Date.dayNames,
            nextText          : 'Nākamais mēnesis (Control+pa labi)',
            prevText          : 'Iepriekšējais mēnesis (Control+pa kreisi)',
            monthYearText     : 'Mēneša izvēle (Control+uz augšu/uz leju lai pārslēgtu gadus)',
            todayTip          : "{0} (Tukšumzīme)",
            format            : "d.m.Y",
            startDay          : 1
        });
    }

    if(exists('Ext.toolbar.Paging')){
        Ext.apply(Ext.PagingToolbar.prototype, {
            beforePageText : "Lapa",
            afterPageText  : "no {0}",
            firstText      : "Pirmā lapa",
            prevText       : "iepriekšējā lapa",
            nextText       : "Nākamā lapa",
            lastText       : "Pēdējā lapa",
            refreshText    : "Atsvaidzināt",
            displayMsg     : "Rāda no {0} līdz {1} ierakstiem, kopā {2}",
            emptyMsg       : 'Nav datu, ko parādīt'
        });
    }

    if(exists('Ext.form.field.Text')){
        Ext.apply(Ext.form.field.Text.prototype, {
            minLengthText : "Minimālais garums šim laukam ir {0}",
            maxLengthText : "Maksimālais garums šim laukam ir {0}",
            blankText     : "Šis ir obligāts lauks",
            regexText     : "",
            emptyText     : null
        });
    }

    if(exists('Ext.form.field.Number')){
        Ext.apply(Ext.form.field.Number.prototype, {
            minText : "Minimālais garums šim laukam ir  {0}",
            maxText : "Maksimālais garums šim laukam ir  {0}",
            nanText : "{0} nav pareizs skaitlis"
        });
    }

    if(exists('Ext.form.field.Date')){
        Ext.apply(Ext.form.field.Date.prototype, {
            disabledDaysText  : "Atspējots",
            disabledDatesText : "Atspējots",
            minText           : "Datumam šajā laukā jābūt lielākam kā {0}",
            maxText           : "Datumam šajā laukā jābūt mazākam kā {0}",
            invalidText       : "{0} nav pareizs datums - tam jābūt šādā formātā: {1}",
            format            : "d.m.Y"
        });
    }

    if(exists('Ext.form.field.ComboBox')){
        Ext.apply(Ext.form.field.ComboBox.prototype, {
            valueNotFoundText : undefined
        });
        Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
            loadingText       : "Ielādē..."
        });
    }

    if(exists('Ext.form.field.VTypes')){
        Ext.apply(Ext.form.field.VTypes, {
            emailText    : 'Šajā laukā jāieraksta e-pasta adrese formātā "lietotās@domēns.lv"',
            urlText      : 'Šajā laukā jāieraksta URL formātā "http:/'+'/www.domēns.lv"',
            alphaText    : 'Šis lauks drīkst saturēt tikai burtus un _ zīmi',
            alphanumText : 'Šis lauks drīkst saturēt tikai burtus, ciparus un _ zīmi'
        });
    }

    if(exists('Ext.grid.header.Container')){
        Ext.apply(Ext.grid.header.Container.prototype, {
            sortAscText  : "Kārtot pieaugošā secībā",
            sortDescText : "Kārtot dilstošā secībā",
            lockText     : "Noslēgt kolonnu",
            unlockText   : "Atslēgt kolonnu",
            columnsText  : "Kolonnas"
        });
    }

    if(exists('Ext.grid.PropertyColumnModel')){
        Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
            nameText   : "Nosaukums",
            valueText  : "Vērtība",
            dateFormat : "j.m.Y"
        });
    }

});