<?php

ini_set('soap.wsdl_cache_enabled', '0');

class CustomerCarbonFootprint
{
    public $id;
    public $footprint;
}

function getInMemoryData() {
    return [
        1 => ['id' => 1, 'footprint' => 15137],
        2 => ['id' => 2, 'footprint' => 16428],
        3 => ['id' => 3, 'footprint' => 20429],
        4 => ['id' => 4, 'footprint' => 19702],
        5 => ['id' => 5, 'footprint' => 33333],
    ];
}

function getFootprint($customer)
{
    $inMemoryStorage = getInMemoryData();

    if (isset($inMemoryStorage[$customer->id])) {
        return $inMemoryStorage[$customer->id]['footprint'];
    }

    return -1;
}

// SOAP server --------------------------------------------------

$soapServer = new SoapServer(
    'carbon-footprints.wsdl',
    [ 'classmap' => [ 'customercf' => 'CustomerCarbonFootprint']]
);


$soapServer->addFunction('getFootprint');
$soapServer->handle();
