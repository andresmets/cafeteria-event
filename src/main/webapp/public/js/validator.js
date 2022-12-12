var Validator = {
    validateCheckOut : function(modal){
        let cashReceived = $(modal).find("input[name='cashReceived']").val();
        return cashReceived != '' && cashReceived >= parseFloat($(modal).find(".amount").text());
    }
}