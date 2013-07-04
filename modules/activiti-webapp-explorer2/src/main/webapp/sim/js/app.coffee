
post = (url, data, success) ->
    $.ajax {
        type: 'POST'
        contentType: 'application/json'
        dataType: 'json'
        "url": url
        "data": JSON.stringify(data)
        "success": success    
    }

$ ->
    $.getJSON 'customer', {}, ((data) ->
        $('select[name=customer]').html(
            data.map((i) ->
                "<option value='#{i.id}'>#{i.name} #{i.surname}</option>"
            ).join('')
        )
    )

    $('input.btn[name=create]').on 'click', ->
        form = document.getElementById 'formCreateCustomer'
        data =
            name: form['name'].value
            surname: form['surname'].value
            numberOfKMH: form['numberOfKMH'].value
            hasOpenCredit: form['hasOpenCredit'].checked
            finansMass: form['finansMaas'].checked
            lastSalaryPayment: form['lastSalaryPayment'].value
        post('customer', data, ((customer)->
                console.log 'success'
                $("<option value='#{customer.id}'>#{customer.name} #{customer.surname}</option>").appendTo('select[name=customer]')
            )
        )

    $('input.btn[name=startcampaign]').on 'click', ->
        customer = $('select[name=customer]').val()
        post "campaign/start/process/#{customer}", {}, ((data) ->
            console.log 'success', data
        )

    $('input.btn[name=usecredit]').on 'click', ->
        form = document.getElementById 'formAction'
        customer = $('select[name=customer]').val()
        post "campaign/useCredit/#{customer}", {}, (-> console.log 'success')

    $('input.btn[name=closekmh]').on 'click', ->
        form = document.getElementById 'formAction'
        customer = $('select[name=customer]').val()
        enforce = form['enforce'].checked
        kmh = Math.round(Math.random() * 100)
        post "campaign/closekmh/#{customer}/#{enforce}/#{kmh}", {}, (-> console.log 'success')

    $('input.btn[name=ofodo]').on 'click', ->
        form = document.getElementById 'formAction'
        customer = $('select[name=customer]').val()
        ofo = form['ofo'].value
        post "campaign/ofo/#{customer}/#{ofo}", {}, (-> console.log 'success')
