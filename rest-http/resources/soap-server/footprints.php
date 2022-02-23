<?php

ini_set('soap.wsdl_cache_enabled', '0');

class Footprint
{
    public $ID;
    public $Footprint;

    function __construct(int $id, int $footprint) {
        $this->ID        = $id;
        $this->Footprint = $footprint;
    }
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
    $footprint       = -1;
    $inMemoryStorage = getInMemoryData();

    if (isset($inMemoryStorage[$customer->ID])) {
        $footprint = $inMemoryStorage[$customer->ID]['footprint'];
    }

    return new Footprint($customer->ID, $footprint);
}

// SOAP server --------------------------------------------------

$soapServer = new SoapServer(
    'carbon-footprints.wsdl',
    [ 'classmap' => [ 'Footprint' => 'Footprint']]
);


$soapServer->addFunction('getFootprint');
$soapServer->handle();
