<?php

ini_set('soap.wsdl_cache_enabled', '0');

function getInMemoryData() {
    return [
        'customer-a' => ['measure' => 15137.11],
        'customer-b' => ['measure' => 16428.22],
        'customer-c' => ['measure' => 20429.33],
        'customer-d' => ['measure' => 19702.44],
        'customer-e' => ['measure' => 33333.55],
    ];
}

function CarbonFootprint($customer)
{
    $measure         = -1.0;
    $inMemoryStorage = getInMemoryData();

    if (isset($inMemoryStorage[$customer->ID])) {
        $measure = $inMemoryStorage[$customer->ID]['measure'];
    }

    return ['CarbonFootprint' => $measure];
}

// SOAP server --------------------------------------------------

$soapServer = new SoapServer(
    'carbon-footprints.wsdl',
    [ 'classmap' => [ 'Footprint' => 'Footprint']]
);


$soapServer->addFunction('CarbonFootprint');
$soapServer->handle();
