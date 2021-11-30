import React, { useEffect, useState } from 'react';
import { PageSection, Title, Grid, GridItem } from '@patternfly/react-core';
import { CovidDataCard } from './CovidDataCard';
import { getCovidDataEnriched } from "../services/CovidDataService";
import { CovidDataEnriched } from '../models/CovidDataEnriched';


export function Dashboard(): JSX.Element {
    const [covidDatas, setCovidData] = useState<CovidDataEnriched[]>([]);


    useEffect(() => {
        getCovidDataEnriched()
            .then((covidDatas) => {
                setCovidData(covidDatas);
            });
    }, []);

    return (
        <PageSection>
            <Title headingLevel="h1" size="lg">Wind Turbines Dashboard</Title>
            <Grid hasGutter>
            {covidDatas.map(covidData =>
                <GridItem key={covidData.countryCode + "-" + covidData.yearWeekISO}>
                    <CovidDataCard
                        covidData={covidData}
                    ></CovidDataCard>
                </GridItem>
            )}
            </Grid>
        </PageSection>
    )
}

