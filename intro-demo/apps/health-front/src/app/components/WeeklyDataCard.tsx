import React, { useState } from 'react';
import {
	Card,
	CardHeader,
	CardTitle,
	CardBody,
	CardExpandableContent,
	Level,
	GridItem
} from '@patternfly/react-core';
import { CovidDataEnriched } from '../models/CovidDataEnriched';
import { CovidDataCard } from './CovidDataCard';


interface CovidDataProps {
  yearWeek: string
	covidDatas: CovidDataEnriched[]
}

interface CardState {
	isCardExpanded: boolean;
	isDropdownOpen: boolean;
}

const WeeklyDataCard = (props: CovidDataProps): JSX.Element => {

    const [state, setState] = useState<CardState>({
		isCardExpanded: false,
		isDropdownOpen: false
	});

	const onCardExpand = () => {
		setState({
			isCardExpanded: !state.isCardExpanded,
			isDropdownOpen: false
		});
    };
  
	const onActionToggle = () => {
		setState({
			isCardExpanded: !state.isCardExpanded,
			isDropdownOpen: !state.isDropdownOpen
		});
	};

	const onActionSelect = () => {
		setState({
			isCardExpanded: false,
			isDropdownOpen: !state.isDropdownOpen
		});
	};

	return (
		<Card id="horizontal card" isExpanded={state.isCardExpanded}>
        <CardHeader
          onExpand={onCardExpand}
          toggleButtonProps={{
            id: 'toggle-button',
            'aria-label': 'Actions',
            'aria-labelledby': 'titleId toggle-button',
            'aria-expanded': state.isCardExpanded
          }}
        >
          {state.isCardExpanded && <CardTitle id="titleId">{props.yearWeek}</CardTitle>}
          {!state.isCardExpanded && (
            <Level hasGutter>
              <CardTitle id="titleId">{props.yearWeek}</CardTitle>
            </Level>
          )}
        </CardHeader>
        <CardExpandableContent>
          <CardBody>
            {
              props.covidDatas.map(covidData =>
                <GridItem key={covidData.countryCode + "-" + covidData.yearWeekISO}>
                    <CovidDataCard
                        covidData={covidData}
                    ></CovidDataCard>
                </GridItem>
            )
            }
          </CardBody>
        </CardExpandableContent>
      </Card>
	);
}

export { WeeklyDataCard };

