import React, { useState } from 'react';
import {
	Card,
	CardHeader,
	CardTitle,
	CardBody,
	CardExpandableContent,
	Level,
	GridItem,
  Grid
} from '@patternfly/react-core';
import { CovidDataEnriched } from '../models/CovidDataEnriched';
import { CovidDataCard } from './CovidDataCard';


interface CovidDataProps {
  yearWeek: string
	covidDatas: CovidDataEnriched[]
}

interface CardState {
	isCardExpanded: boolean;
}

const WeeklyDataCard = (props: CovidDataProps): JSX.Element => {

    const [state, setState] = useState<CardState>({
		isCardExpanded: true
	});

	const onCardExpand = () => {
		setState({
			isCardExpanded: !state.isCardExpanded
      });
    };

	return (
		<Card id="weeklyDataCard" isExpanded={state.isCardExpanded}>
        <CardHeader
          onExpand={onCardExpand}
          toggleButtonProps={{
            id: 'toggleButton',
            'aria-label': 'Actions',
            'aria-labelledby': 'cardTitle toggleButton',
            'aria-expanded': state.isCardExpanded
          }}
        >
          {state.isCardExpanded && <CardTitle id="cardTitle">{props.yearWeek}</CardTitle>}
          {!state.isCardExpanded && (
            <Level hasGutter>
              <CardTitle id="cardTitle">{props.yearWeek}</CardTitle>
            </Level>
          )}
        </CardHeader>
        <CardExpandableContent>
          <CardBody>
          <Grid hasGutter>
            {
              props.covidDatas.map(covidData =>
                <GridItem key={covidData.countryCode + "-" + covidData.yearWeekISO + "-" + covidData.vaccine}>
                    <CovidDataCard 
                        covidData={covidData}
                    ></CovidDataCard>
                </GridItem>
            )
            }
            </Grid>
          </CardBody>
        </CardExpandableContent>
      </Card>
	);
}

export { WeeklyDataCard };

