/*!
 * Extensible 1.5.1
 * Copyright(c) 2010-2012 Extensible, LLC
 * licensing@ext.ensible.com
 * http://ext.ensible.com
 */
/**
 * List compiled by mystix on the extjs.com forums.
 * Thank you Mystix!
 *
 * English Translations
 * updated to 2.2 by Condor (8 Aug 2008)
 */
Ext.onReady(function() {
    var cm = Ext.ClassManager, 
        exists = Ext.Function.bind(cm.get, cm);

    if (Ext.Updater) {
        Ext.Updater.defaults.indicatorText = '<div class="loading-indicator">Loading...</div>';
    }

    if(exists('Ext.data.Types')){
        Ext.data.Types.stripRe = /[\$,%]/g;
    }

    if(exists('Ext.view.View')){
      Ext.view.View.prototype.emptyText = "";
    }

    if(exists('Ext.grid.Panel')){
      Ext.grid.Panel.prototype.ddText = "{0} selected row{1}";
    }

    if(Ext.LoadMask){
      Ext.LoadMask.prototype.msg = "Loading...";
    }
    
    if(Ext.Date) {
        Ext.Date.monthNames = [
          "January",
          "February",
          "March",
          "April",
          "May",
          "June",
          "July",
          "August",
          "September",
          "October",
          "November",
          "December"
        ];

        Ext.Date.getShortMonthName = function(month) {
          return Ext.Date.monthNames[month].substring(0, 3);
        };

        Ext.Date.monthNumbers = {
          Jan : 0,
          Feb : 1,
          Mar : 2,
          Apr : 3,
          May : 4,
          Jun : 5,
          Jul : 6,
          Aug : 7,
          Sep : 8,
          Oct : 9,
          Nov : 10,
          Dec : 11
        };

        Ext.Date.getMonthNumber = function(name) {
          return Ext.Date.monthNumbers[name.substring(0, 1).toUpperCase() + name.substring(1, 3).toLowerCase()];
        };

        Ext.Date.dayNames = [
          "Sunday",
          "Monday",
          "Tuesday",
          "Wednesday",
          "Thursday",
          "Friday",
          "Saturday"
        ];

        Ext.Date.getShortDayName = function(day) {
          return Ext.Date.dayNames[day].substring(0, 3);
        };

        Ext.Date.parseCodes.S.s = "(?:st|nd|rd|th)";
    }
    
    if(Ext.MessageBox){
      Ext.MessageBox.buttonText = {
        ok     : "OK",
        cancel : "Cancel",
        yes    : "Yes",
        no     : "No"
      };
    }

    if(exists('Ext.util.Format')){
        Ext.apply(Ext.util.Format, {
            thousandSeparator: ',',
            decimalSeparator: '.',
            currencySign: '$',
            dateFormat: 'm/d/Y'
        });
    }

    if(exists('Ext.picker.Date')){
      Ext.apply(Ext.picker.Date.prototype, {
        todayText         : "Today",
        minText           : "This date is before the minimum date",
        maxText           : "This date is after the maximum date",
        disabledDaysText  : "",
        disabledDatesText : "",
        monthNames        : Ext.Date.monthNames,
        dayNames          : Ext.Date.dayNames,
        nextText          : 'Next Month (Control+Right)',
        prevText          : 'Previous Month (Control+Left)',
        monthYearText     : 'Choose a month (Control+Up/Down to move years)',
        todayTip          : "{0} (Spacebar)",
        format            : "m/d/y",
        startDay          : 0
      });
    }

    if(exists('Ext.picker.Month')) {
      Ext.apply(Ext.picker.Month.prototype, {
          okText            : "&#160;OK&#160;",
          cancelText        : "Cancel"
      });
    }

    if(exists('Ext.toolbar.Paging')){
      Ext.apply(Ext.PagingToolbar.prototype, {
        beforePageText : "Page",
        afterPageText  : "of {0}",
        firstText      : "First Page",
        prevText       : "Previous Page",
        nextText       : "Next Page",
        lastText       : "Last Page",
        refreshText    : "Refresh",
        displayMsg     : "Displaying {0} - {1} of {2}",
        emptyMsg       : 'No data to display'
      });
    }

    if(exists('Ext.form.Basic')){
        Ext.form.Basic.prototype.waitTitle = "Please Wait...";
    }

    if(exists('Ext.form.field.Base')){
      Ext.form.field.Base.prototype.invalidText = "The value in this field is invalid";
    }

    if(exists('Ext.form.field.Text')){
      Ext.apply(Ext.form.field.Text.prototype, {
        minLengthText : "The minimum length for this field is {0}",
        maxLengthText : "The maximum length for this field is {0}",
        blankText     : "This field is required",
        regexText     : "",
        emptyText     : null
      });
    }

    if(exists('Ext.form.field.Number')){
      Ext.apply(Ext.form.field.Number.prototype, {
        decimalSeparator : ".",
        decimalPrecision : 2,
        minText : "The minimum value for this field is {0}",
        maxText : "The maximum value for this field is {0}",
        nanText : "{0} is not a valid number"
      });
    }

    if(exists('Ext.form.field.Date')){
      Ext.apply(Ext.form.field.Date.prototype, {
        disabledDaysText  : "Disabled",
        disabledDatesText : "Disabled",
        minText           : "The date in this field must be after {0}",
        maxText           : "The date in this field must be before {0}",
        invalidText       : "{0} is not a valid date - it must be in the format {1}",
        format            : "m/d/y",
        altFormats        : "m/d/Y|m-d-y|m-d-Y|m/d|m-d|md|mdy|mdY|d|Y-m-d"
      });
    }

    if(exists('Ext.form.field.ComboBox')){
      Ext.apply(Ext.form.field.ComboBox.prototype, {
        valueNotFoundText : undefined
      });
        Ext.apply(Ext.form.field.ComboBox.prototype.defaultListConfig, {
            loadingText       : "Loading..."
        });
    }

    if(exists('Ext.form.field.VTypes')){
      Ext.apply(Ext.form.field.VTypes, {
        emailText    : 'This field should be an e-mail address in the format "user@example.com"',
        urlText      : 'This field should be a URL in the format "http:/'+'/www.example.com"',
        alphaText    : 'This field should only contain letters and _',
        alphanumText : 'This field should only contain letters, numbers and _'
      });
    }

    if(exists('Ext.form.field.HtmlEditor')){
      Ext.apply(Ext.form.field.HtmlEditor.prototype, {
        createLinkText : 'Please enter the URL for the link:',
        buttonTips : {
          bold : {
            title: 'Bold (Ctrl+B)',
            text: 'Make the selected text bold.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          italic : {
            title: 'Italic (Ctrl+I)',
            text: 'Make the selected text italic.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          underline : {
            title: 'Underline (Ctrl+U)',
            text: 'Underline the selected text.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          increasefontsize : {
            title: 'Grow Text',
            text: 'Increase the font size.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          decreasefontsize : {
            title: 'Shrink Text',
            text: 'Decrease the font size.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          backcolor : {
            title: 'Text Highlight Color',
            text: 'Change the background color of the selected text.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          forecolor : {
            title: 'Font Color',
            text: 'Change the color of the selected text.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          justifyleft : {
            title: 'Align Text Left',
            text: 'Align text to the left.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          justifycenter : {
            title: 'Center Text',
            text: 'Center text in the editor.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          justifyright : {
            title: 'Align Text Right',
            text: 'Align text to the right.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          insertunorderedlist : {
            title: 'Bullet List',
            text: 'Start a bulleted list.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          insertorderedlist : {
            title: 'Numbered List',
            text: 'Start a numbered list.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          createlink : {
            title: 'Hyperlink',
            text: 'Make the selected text a hyperlink.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          },
          sourceedit : {
            title: 'Source Edit',
            text: 'Switch to source editing mode.',
            cls: Ext.baseCSSPrefix + 'html-editor-tip'
          }
        }
      });
    }

    if(exists('Ext.grid.header.Container')){
      Ext.apply(Ext.grid.header.Container.prototype, {
        sortAscText  : "Sort Ascending",
        sortDescText : "Sort Descending",
        columnsText  : "Columns"
      });
    }

    if(exists('Ext.grid.GroupingFeature')){
      Ext.apply(Ext.grid.GroupingFeature.prototype, {
        emptyGroupText : '(None)',
        groupByText    : 'Group By This Field',
        showGroupsText : 'Show in Groups'
      });
    }

    if(exists('Ext.grid.PropertyColumnModel')){
      Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
        nameText   : "Name",
        valueText  : "Value",
        dateFormat : "m/j/Y",
        trueText: "true",
        falseText: "false"
      });
    }

    if(exists('Ext.grid.BooleanColumn')){
       Ext.apply(Ext.grid.BooleanColumn.prototype, {
          trueText  : "true",
          falseText : "false",
          undefinedText: '&#160;'
       });
    }

    if(exists('Ext.grid.NumberColumn')){
        Ext.apply(Ext.grid.NumberColumn.prototype, {
            format : '0,000.00'
        });
    }

    if(exists('Ext.grid.DateColumn')){
        Ext.apply(Ext.grid.DateColumn.prototype, {
            format : 'm/d/Y'
        });
    }


    if(exists('Ext.form.field.Time')){
      Ext.apply(Ext.form.field.Time.prototype, {
        minText : "The time in this field must be equal to or after {0}",
        maxText : "The time in this field must be equal to or before {0}",
        invalidText : "{0} is not a valid time",
        format : "g:i A",
        altFormats : "g:ia|g:iA|g:i a|g:i A|h:i|g:i|H:i|ga|ha|gA|h a|g a|g A|gi|hi|gia|hia|g|H"
      });
    }

    if(exists('Ext.form.CheckboxGroup')){
      Ext.apply(Ext.form.CheckboxGroup.prototype, {
        blankText : "You must select at least one item in this group"
      });
    }

    if(exists('Ext.form.RadioGroup')){
      Ext.apply(Ext.form.RadioGroup.prototype, {
        blankText : "You must select one item in this group"
      });
    }
});