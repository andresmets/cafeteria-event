var Ajax = {
    getDefaultHeaders : function(){
        return { "Accept-Language" : $("html").attr("lang") };
    },
    load : function(uri, callback, config){
    return new Promise(resolve => {
          $.ajax({
                    headers : Ajax.getDefaultHeaders(),
                    url: uri
                }).done(function(response){
                    if(callback != null){
                        callback(response, config);
                    }
                    response.config=config;
                    resolve(response);
                });
            });
    },
    post : function(uri, callback, config, data){
        let headers = Ajax.getDefaultHeaders();
        headers["Content-Type"] = "application/json"
        return new Promise((resolve) => {
              $.ajax({
                        headers : headers,
                        url:  uri,
                        type: "POST",
                        data: JSON.stringify(data)
                    }).done(function(response){
                        if(callback != null){
                            callback(response, config);
                        }
                        response.config = config;
                        resolve(response);
                    });
                });
        }
}
var API = {
    API_VERSION : "/api/v3"
}
var Products = {
    STATUS_200 : "200",
    URI_TYPES : API.API_VERSION.concat("/types"),
    URI_PRODUCTS : API.API_VERSION.concat("/products/"),
    URI_PRODUCT: API.API_VERSION.concat("/product/"),
    URI_RESERVED : API.API_VERSION.concat("/reserved"),
    URI_SHOPPING_BASKET: API.API_VERSION.concat("/basket"),
    URI_SELECT_ITEM: API.API_VERSION.concat("/select"),
    URI_REMOVE_ITEM: API.API_VERSION.concat("/remove"),
    URI_CANCEL_RESERVATION: API.API_VERSION.concat("/cancel"),

    loadTypes : function(){
        return Ajax.load(Products.URI_TYPES, Products.addTypes)
    },
    loadProducts : function(type){
        Ajax.load("/products/".concat(type), Products.addProducts);
    },
    addTypes : function(apiResponse){
        for(i=0;i<apiResponse.response.length;i++){
            $(".types").loadTemplate($("#item-category"), {"type":apiResponse.response[i].type, "label":Products.loadLabel(apiResponse.response[i].type, apiResponse.labels)}, { append: true, isFile: false});
        }
        $(".types").show();
    },
    addProducts : function(apiResponse, config){
        let productItems;
        for(i=0;i<apiResponse.response.length;i++){
           let templateObject = $.extend(true, apiResponse.response[i], apiResponse.labels)
           productItems = $(Products.getProductTypeContainer(config)).loadTemplate($("#item-template"), templateObject, { append: true, isFile: false});
        }
        $(productItems).find(".shopping-item .item-name").each(function(index, element){
            $(element).click(Products.productClickHandler);
            $(element).on("mouseover", Products.productMouseOverHandler);
        });
        $(productItems).find(".icon.delete .delete").each((index, element)=>{
            $(element).click(Products.removeFromBasket)
        })
    },
    addProductTotals : function(apiResponse, config){
        if(config != undefined){
            $(Products.getProductTypeContainer(config)).find(".shopping-item").each((index, value) => {
                let productId = $(value).data("item-id");
                let reserved = (apiResponse != null && apiResponse.response[productId] != undefined) ? apiResponse.response[productId] : 0;
                let counts = $(value).find(".counts");
                $(counts).text(Formats.formatItemCounts($(counts).data("item-count"), reserved));
                Products.toggleReadonly(value, parseInt($(counts).data("item-count")));
                /*
                if(parseInt($(counts).data("item-count")) == 0){
                    Products.setItemReadOnly(value, true);
                }else{
                    Products.setItemReadOnly(value, false);
                }*/
            });
        }
    },
    toggleReadonly : function(element, count){
        if(count == 0){
            Products.setItemReadOnly(element, true);
        }else{
            Products.setItemReadOnly(element, false);
        }
    },
    addShoppingBasket : function(apiResponse, config){
        if(apiResponse.response.totals != undefined){
            let container = $(Products.getProductTypeContainer(config));
            $(container).find(".shopping-item").each((index, elem) => {
                let productId = $(elem).data("item-id");
                if(apiResponse.response.totals[productId] != undefined){
                    Products.addCountInTheBasket(elem, apiResponse.response.totals[productId], apiResponse.response.reservation.id)
                }
            });
            Products.addCheckoutTotals(apiResponse);
          }
    },
    removeFromBasket : function(e){
        if($(e.target).data("basket-id") != ''){
            Products.postProductInfo(Products.URI_REMOVE_ITEM, $(e.target).closest(".shopping-item").data("item-id"), $(e.target).closest(".shopping-item").parent());
        }
    },
    addCheckoutTotals : function(apiResponse){
        $(".checkout-info").remove();
        let templateObject = {};
        if(apiResponse.response.reservation  != undefined){
            let products = apiResponse.response.reservation.product;
            for(i = 0; i< products.length;i++){
                templateObject["amount"] = templateObject["amount"] != undefined ? templateObject["amount"] +  products[i].price : products[i].price;
            }
            templateObject["amount"] = templateObject["amount"] / 100;
            $.extend(templateObject, apiResponse.labels);
            CheckOut.addCheckOutTotalListeners($("body").loadTemplate($("#shopping-basket-totals"), templateObject, { append: true, isFile: false}), templateObject);
        }
    },
    postProductInfo : function(URI, productId, container){
         Ajax.post(URI, null, null, {"productId" : productId}).then(function(response){
                if(response.status == Products.STATUS_200){
                   Loader.load();
                   Products.loadProductsByTypes();
                }else{
                   Exception.handleItemsReserved(e);
                }
           });
    },
    loadLabel : function(key, object){
        return object[key];
    },
    loadProductsByTypes : function(){
        $(".shopping-item").remove();
        $(".row").each(function(index, elem){
            let typeHeading = $(elem).find("h2");
            let type = $(typeHeading).data("type");
            Ajax.load(Products.URI_PRODUCTS.concat(type), Products.addProducts, {"type":type})
            .then(function(response){
                Ajax.load(Products.URI_RESERVED, Products.addProductTotals, {"type":type});
                Ajax.load(Products.URI_SHOPPING_BASKET, Products.addShoppingBasket, {"type":type});
            });
        });
    },
    productClickHandler : function(e){
        e.preventDefault();
        Products.postProductInfo(Products.URI_SELECT_ITEM, $(e.target).data("item-id"), $(e.target).parent().parent());
    },
    addCountInTheBasket : function(element, count, basketId){
            let elem = $(element).find(".selected");
            let currentCount =  parseInt($(elem).text());
            $(elem).text(($(elem).text() > 0 ? parseInt($(elem).text())+1 : count));
            $(element).find(".icon.delete .delete").data("basket-id", basketId);
    },
    getProductTypeContainer : function(config){
        return  $(".row *[data-type=" + config.type + "]").parent();
    },
    setItemReadOnly : function(element, toggle){
        if(toggle){
            $(element).addClass("read-only")
        }else{
            $(element).removeClass("read-only")
        }
    },
    productMouseOverHandler : function(e){
        let item = $(e.target).closest(".shopping-item");
        let counts = $(item).find(".counts");
        $(counts).text("");
        let loader = Loader.getPulseLoader(counts);
        let count, reserved;
        const product = Ajax.load(Products.URI_PRODUCT.concat($(item).data("item-id")), (response, config) => {count = response.response.quantity;$(counts).data("item-count", count) }, null);
        const total = Ajax.load(Products.URI_RESERVED, (response, config) => {reserved = response.response[$(item).data("item-id")] != undefined ? response.response[$(item).data("item-id")] : 0}, null);
        const promises = [product, total];
        Promise.allSettled(promises).then((results) => {
                $(counts).text(Formats.formatItemCounts(count, reserved));
                Products.toggleReadonly(item, count);
                Loader.hideLoader(loader);
            }
        );
    },
    domReadyHandler : function(){
        Products.loadTypes().then(function(){
            Products.loadProductsByTypes();
        });

    }
}
var Exception = {
    handleItemsReserved : function(e){

    },
    handleCashNotReceived : function(e, element, response){
        $(element).find(".modal").loadTemplate($("#shopping-basket-error"), response, {prepend:true, isFile: false});
    }
}
var Formats = {
    formatItemCounts : function(items, reserved){
        return ("").concat(items).concat(" / ").concat(reserved);
    }
}
var Overlay = {
    getOverlayDimensions : function(){
        let dimensions = {
            height: $("html")[0].getBoundingClientRect().height,
            width: $("html")[0].getBoundingClientRect().width
        }
        return dimensions;
    },
    appendOverlay : function(){
        let overlay = $("<div class=\"overlay loader\"></div>");
        let dimensions = Overlay.getOverlayDimensions();
        $(overlay).height(dimensions.height);
        $(overlay).width(dimensions.width);
        $("body").append(overlay);
    },
    positionOverlay : function(overlay){
        let overlayDimensions = Overlay.getOverlayDimensions();
        $(overlay).height(overlayDimensions.height);
        $(overlay).width(overlayDimensions.width);
    }
}
var Loader = {
    PULSE_LOADER_SELECTOR : ".loader-pulse",
    load : function(){
        Overlay.appendOverlay();
        $(".loader").fadeOut(2000, () => {$(".overlay.loader").remove()})
    },
    addPulseLoader : function(element){
        let loader = $("<div class=\"loader-pulse\"></div>");
        $(element).append(loader);
        return loader;
    },
    getPulseLoader : function(element){
        let loader = $(element).find(Loader.PULSE_LOADER_SELECTOR);
        if($(loader).length > 0){
            return loader;
        }else{
            return Loader.addPulseLoader(element);
        }
    },
    hideLoader : function(loader){
        $(loader).remove();
    }
}
var Modals = {
     createModal : function(templateName, templateObject, templateConfig){
         $("body").loadTemplate(templateName, templateObject, templateConfig);
     },
     getModalPosition : function(modal){
      let position = {};
      let top = $("body")[0].getBoundingClientRect().height/2;
      let left = $("body")[0].getBoundingClientRect().width/2
      let modalHeight = $(modal).height();
      let modalWidth = $(modal).width();
      position.top     =   top-(modalHeight/2);
      position.left    =   left-(modalWidth/2);
      return position;
    },
    positionModal : function(modal){
        let position = Modals.getModalPosition(modal);
        $(modal).css("top", position.top);
        $(modal).css("left", position.left);
    },
    addModalListeners : function(modal, callBacks){
        for(i=0;i<callBacks.length;i++){
            callBacks[i]();
        }
        $(modal).find(".cancel").click(()=>{
            $(modal).remove();
        });
    }
}
var CheckOut = {
    URI_COMMIT_TRANSACTION : "/sell",
    CSS_SELECTOR_AMOUNT : ".amount",
    CSS_SELECTOR_CASH_RECEIVED : "input[name='cashReceived']",
    CSS_SELECTOR_AMOUNT_RETURNED : ".returned",

    calculateReturnedAmount : function(modal){
        let payed =  parseFloat($(modal).find(CheckOut.CSS_SELECTOR_CASH_RECEIVED).val());
        let amount = parseFloat($(modal).find(CheckOut.CSS_SELECTOR_AMOUNT).text());
        return (payed - amount).toFixed(2);
    },
    setAmountReturned : function(modal){
        $(modal).find(CheckOut.CSS_SELECTOR_AMOUNT_RETURNED).text(CheckOut.calculateReturnedAmount(modal));
    },
    checkOutProducts : function(modal, templateObject){
         let data = {
                "received": parseFloat($(modal).find(CheckOut.CSS_SELECTOR_CASH_RECEIVED).val()),
                "returned": $(modal).find(CheckOut.CSS_SELECTOR_AMOUNT_RETURNED).text(),
                "amount": parseFloat($(modal).find(CheckOut.CSS_SELECTOR_AMOUNT).text())
         }
         $.extend(templateObject, {"errorLabel":templateObject.labelReserved});
         Ajax.post(CheckOut.URI_COMMIT_TRANSACTION, null, null, data).then((response) =>
         {
            if(response.status != Products.STATUS_200){
                Modals.createModal("#error-modal", templateObject, { append: true, isFile: false, afterInsert:
                                                                                               function(element){
                                                                                                   Modals.positionModal($(element).find(".modal"));
                                                                                                   Modals.addModalListeners(element, new ArrayList());
                                                                                   }});

            }else{
                Products.loadProductsByTypes();
                $(modal).remove();
            }
         });
    },
    addCheckOutTotalListeners : function(element, templateObject){
        $(element).find(".checkout").click((e) => {
            Modals.createModal("#shopping-basket-modal", templateObject,
            { append: true, isFile: false, afterInsert:
                 function(element){
                     Modals.positionModal($(element).find(".modal"));
                     Overlay.positionOverlay($(element));
                 }
             });
            let modal = $(element).find("#shopping-basket");
            let callbacks = new Array();
            callbacks= [()=>{
               $(modal).find(".checkout").click((e) => {
                   $(modal).find(".error").remove();
                   if(Validator.validateCheckOut(modal)){
                        CheckOut.checkOutProducts(modal, templateObject);
                   }else{
                       Exception.handleCashNotReceived(e, modal, templateObject);
                   }
               })
            },
            ()=>{
                CheckOut.addCheckoutEventListener(modal, ".received", "mouseout", templateObject);
            },
            ()=>{
                CheckOut.addCheckoutEventListener(modal, ".received", "change", templateObject);
               }
            ];
            Modals.addModalListeners(modal, callbacks);
        });
        $(element).find(".cancel").click(function(e){
             Loader.load();
             Ajax.post(Products.URI_CANCEL_RESERVATION, null, null, {}).then((response) => {
                Products.loadProductsByTypes();
             });
        });
    },
    addCheckoutEventListener : function(modal,selector,event, templateObject){
            $(modal).find(selector).on(event, (e) => {
                    $(modal).find(".error").remove();
                    if(Validator.validateCheckOut(modal)){
                        CheckOut.setAmountReturned(modal);
                     }else{
                        Exception.handleCashNotReceived(e, modal, templateObject);
                     }
                });
    }
}
var Responsive = {
    domReadyHandler : function(){
        $(window).on("resize", () =>{
            if($(".overlay").length > 0){
                $(".overlay").height($("html")[0].getBoundingClientRect().height);
                $(".overlay").width($("html")[0].getBoundingClientRect().width);
            }
            if($(".modal").length > 0){
                Modals.positionModal($(".modal"));
            }
        });
    }
}
$.addTemplateFormatter("itemPriceFormatter",
    function(value, template) {
        return  parseInt(value)/100 + '\u20AC';
});

$(document).ready(
    function(){
        Products.domReadyHandler();
        Responsive.domReadyHandler();
    }
);

