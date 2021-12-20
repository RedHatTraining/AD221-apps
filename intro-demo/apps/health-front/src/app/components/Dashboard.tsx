import React, { ReactElement, useEffect, useState } from 'react';
import { PageSection, Title, Grid, GridItem } from '@patternfly/react-core';
import { WeeklyDataCard } from './WeeklyDataCard';
import { getCovidDataEnriched } from "../services/CovidDataService";
import { CovidDataEnriched } from '../models/CovidDataEnriched';


const Dashboard = ():JSX.Element => {
    const [covidDatas, setCovidData] = useState<CovidDataEnriched[]>([]);

    
    useEffect(() => {
        setInterval(() => getCovidDataEnriched()
            .then((covidDatas) => {
                setCovidData(covidDatas);
        }), 3000);
    }, []);

    const covidDataWeekMap = new Map();

    const addCovidDataToMap = (covidData:CovidDataEnriched) => {
        let covidDataArr: CovidDataEnriched[] = []

        if(covidDataWeekMap.get(covidData.yearWeekISO) != null){
            covidDataArr = covidDataWeekMap.get(covidData.yearWeekISO);
        }
        
        covidDataArr.push(covidData)
        covidDataWeekMap.set(covidData.yearWeekISO, covidDataArr)
	};

    covidDatas.sort((covidData1,covidData2) => {
        if (covidData1.yearWeekISO > covidData2.yearWeekISO) {
            return 1;
        }
    
        if (covidData1.yearWeekISO < covidData2.yearWeekISO) {
            return -1;
        }
    
        return 0;
    }).map(covidData => addCovidDataToMap(covidData));

    const elems: ReactElement[] = []
    covidDataWeekMap.forEach((value: CovidDataEnriched[], key: string) => elems.push(<GridItem key={key}>
        <WeeklyDataCard
            yearWeek={key}
            covidDatas={value}
        ></WeeklyDataCard>
    </GridItem>));

    return (
        <PageSection>
            <Title headingLevel="h1" size="lg">Covid Data of European Countries by Week Year</Title>
            <Grid hasGutter>
                {elems}
            </Grid>
        </PageSection>
    )
}

export { Dashboard };

