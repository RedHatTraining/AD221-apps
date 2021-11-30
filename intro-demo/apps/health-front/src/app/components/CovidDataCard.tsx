import React, { useState } from 'react';
import {
	Card,
	CardHeader,
	CardTitle,
	CardBody,
	CardExpandableContent,
	Level,
	Label,
	Grid,
	Flex,
	List,
	ListItem,
	Button
} from '@patternfly/react-core';
import { CovidDataEnriched } from '../models/CovidDataEnriched';
import InfoCircleIcon from '@patternfly/react-icons/dist/esm/icons/info-circle-icon';
import ArrowRightIcon from '@patternfly/react-icons/dist/esm/icons/arrow-right-icon';
import ExternalLinkAltIcon from '@patternfly/react-icons/dist/esm/icons/external-link-alt-icon';


interface CovidDataProps {
	covidData: CovidDataEnriched
}

interface CardState {
	isCardExpanded: boolean;
	isDropdownOpen: boolean;
}

const CovidDataCard = (props: CovidDataProps): JSX.Element => {

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

	const flagImage = <img className="flag" src={props.covidData.flagImageURL}/>;

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
          {state.isCardExpanded && <CardTitle id="titleId">{props.covidData.countryName} {flagImage}</CardTitle>}
          {!state.isCardExpanded && (
            <Level hasGutter>
              <CardTitle id="titleId">{props.covidData.countryName} {flagImage}</CardTitle>
            </Level>
          )}
        </CardHeader>
        <CardExpandableContent>
          <CardBody>
            <Grid md={6} lg={3} hasGutter>
              <Flex
                spaceItems={{ default: 'spaceItemsLg' }}
                alignItems={{ default: 'alignItemsFlexStart' }}
                direction={{ default: 'column' }}
              >
                <Flex
                  spaceItems={{ default: 'spaceItemsSm' }}
                  alignItems={{ default: 'alignItemsFlexStart' }}
                  direction={{ default: 'column' }}
                  grow={{ default: 'grow' }}
                >
                  <Label icon={<InfoCircleIcon />} color="blue">
                    Set up your cluster
                  </Label>
                  <p>Continue setting up your cluster to access all you cain in the Console</p>
                  <List isPlain>
                    <ListItem>
                      <a href="#">Add identity provider</a>
                    </ListItem>
                    <ListItem>
                      <a href="#">Configure alert receivers</a>
                    </ListItem>
                    <ListItem>
                      <a href="#">Configure default ingress certificate</a>
                    </ListItem>
                  </List>
                </Flex>
                <Button href="#" component="a" variant="link" isInline icon={<ArrowRightIcon />} iconPosition="right">
                  View all set up cluster steps
                </Button>
              </Flex>
              <Flex
                spaceItems={{ default: 'spaceItemsLg' }}
                alignItems={{ default: 'alignItemsFlexStart' }}
                direction={{ default: 'column' }}
              >
                <Flex
                  spaceItems={{ default: 'spaceItemsSm' }}
                  alignItems={{ default: 'alignItemsFlexStart' }}
                  direction={{ default: 'column' }}
                  grow={{ default: 'grow' }}
                >
                  <Label icon={<InfoCircleIcon />} color="purple">
                    Guided tours
                  </Label>
                  <p>Tour some of the key features around the console</p>
                  <List isPlain>
                    <ListItem>
                      <a href="#">Tour the console</a>
                    </ListItem>
                    <ListItem>
                      <a href="#">Getting started with Serverless</a>
                    </ListItem>
                  </List>
                </Flex>
                <Button href="#" component="a" variant="link" isInline icon={<ArrowRightIcon />} iconPosition="right">
                  View all guided tours
                </Button>
              </Flex>
              <Flex
                spaceItems={{ default: 'spaceItemsLg' }}
                alignItems={{ default: 'alignItemsFlexStart' }}
                direction={{ default: 'column' }}
              >
                <Flex
                  spaceItems={{ default: 'spaceItemsSm' }}
                  alignItems={{ default: 'alignItemsFlexStart' }}
                  direction={{ default: 'column' }}
                  grow={{ default: 'grow' }}
                >
                  <Label icon={<InfoCircleIcon />} color="green">
                    Quick starts
                  </Label>
                  <p>Get started with features using our step-by-step documentation</p>
                  <List isPlain>
                    <ListItem>
                      <a href="#">Getting started with Serverless</a>
                    </ListItem>
                    <ListItem>
                      <a href="#">Explore virtualization</a>
                    </ListItem>
                    <ListItem>
                      <a href="#">Build pipelines</a>
                    </ListItem>
                  </List>
                </Flex>
                <Button href="#" component="a" variant="link" isInline icon={<ArrowRightIcon />} iconPosition="right">
                  View all quick starts
                </Button>
              </Flex>
              <Flex
                spaceItems={{ default: 'spaceItemsLg' }}
                alignItems={{ default: 'alignItemsFlexStart' }}
                direction={{ default: 'column' }}
              >
                <Flex
                  spaceItems={{ default: 'spaceItemsSm' }}
                  alignItems={{ default: 'alignItemsFlexStart' }}
                  direction={{ default: 'column' }}
                  grow={{ default: 'grow' }}
                >
                  <Label icon={<InfoCircleIcon />} color="orange">
                    Learning resources
                  </Label>
                  <p>Learn about new features within the Console and get started with demo apps</p>
                  <List isPlain>
                    <ListItem>
                      <a href="#">See what's possible with the Explore page</a>
                    </ListItem>
                    <ListItem>
                      <a href="#">
                        OpenShift 4.5: Top Tasks
                        <ExternalLinkAltIcon />
                      </a>
                    </ListItem>
                    <ListItem>
                      <a href="#">Try a demo app</a>
                    </ListItem>
                  </List>
                </Flex>
                <Button href="#" component="a" variant="link" isInline icon={<ArrowRightIcon />} iconPosition="right">
                  View all learning resources
                </Button>
              </Flex>
            </Grid>
          </CardBody>
        </CardExpandableContent>
      </Card>
	);
}

export { CovidDataCard };

